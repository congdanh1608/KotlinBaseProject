package com.danhtran.androidbaseproject.extras.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.extras.Constant
import com.danhtran.androidbaseproject.extras.enums.EventBusKey
import com.danhtran.androidbaseproject.utils.DialogUtils
import com.google.android.gms.location.LocationRequest
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider

/**
 * Created by danh.tran on 04/08/16.
 */

class GPSReceiver(private val context: Context, private val listener: GPSReceiverListener?) : BroadcastReceiver() {

    private val appContext: Context
    private var currentType: Int = 0

    //disposable
    private var disposable: Disposable? = null
    private var disposable2: Disposable? = null
    private var disposableUpdated: Disposable? = null

    private val locationProvider: ReactiveLocationProvider
    private val locationRequest: LocationRequest

    private var isFirstReceive = true

    //last current location
    var lastLocation: Location? = null
        private set

    init {
        this.appContext = context.applicationContext                     //must application context
        registerReceiver()

        locationProvider = ReactiveLocationProvider(appContext)

        locationRequest = LocationRequest.create() //standard GMS LocationRequest
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(TIME_GPS_UPDATE)                        //update after every 10s
        //                        .setSmallestDisplacement(MIN_DISTANCE_MOVING);          // 10 meters

    }

    private fun registerReceiver() {
        appContext.registerReceiver(
            this,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )      //must register in this to maintain activity + context
    }

    fun unregisterReceiver() {
        stopUpdateLocation()
        appContext.unregisterReceiver(this)      //must register in this to maintain activity + context
    }

    @SuppressLint("MissingPermission")
    fun requestLocationLastKnow() {
        this.currentType = TYPE_REQUEST_LAST_KNOW

        if (checkGPSEnable(true)) {
            if (checkPermissionLocation(appContext)) {
                listener!!.permission(true)
                //has permission
                disposable = locationProvider.lastKnownLocation
                    //                        .switchIfEmpty(locationProvider.getUpdatedLocation(locationRequest))
                    .switchIfEmpty(object : Observable<Location>() {
                        override fun subscribeActual(observer: Observer<in Location>?) {
                            /*FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext);
                                fusedLocationClient.getLastLocation()
                                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                                            @Override
                                            public void onSuccess(Location location) {
                                                if (location == null) {
                                                    //try again
                                                    Log.d("GPS Tracker", "try again");
                                                    requestLocationLastKnowDelay();
                                                } else {
                                                    Log.d("GPS Tracker", "last know location 1");
                                                    listener.location(location);
                                                    GPSReceiver.this.lastLocation = location;
                                                }
                                            }
                                        });*/
                            stopLastKnowLocationRequest()
                            disposable2 = locationProvider.getUpdatedLocation(locationRequest)
                                .subscribe({ location ->
                                    listener.location(location)
                                    this@GPSReceiver.lastLocation = location
                                    Log.d("GPS Tracker", "update new location 1")
                                }, { throwable -> Logger.d(throwable) })
                        }
                    })
                    .subscribe({ location ->
                        Log.d("GPS Tracker", "last know location 2")
                        listener.location(location)
                        this@GPSReceiver.lastLocation = location
                    }, { throwable -> Logger.d(throwable) })
            } else {
                //has no permission
                listener!!.permission(false)
                stopUpdateLocation()
            }
        } else {
            stopUpdateLocation()
        }
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdate() {
        this.currentType = TYPE_REQUEST_UPDATE

        if (checkGPSEnable(true)) {
            if (checkPermissionLocation(appContext)) {
                listener!!.permission(true)
                //has permission
                Log.d("GPS Tracker", "request location update")
                disposableUpdated = locationProvider.getUpdatedLocation(locationRequest)
                    .subscribe({ location ->
                        listener.location(location)
                        this@GPSReceiver.lastLocation = location
                        Log.d("GPS Tracker", "update new location")
                    }, { throwable -> Logger.d(throwable.message) })
            } else {
                //has no permission
                listener!!.permission(false)
                stopUpdateLocation()
            }
        } else {
            stopUpdateLocation()
        }
    }

    fun stopLastKnowLocationRequest() {
        if (disposable2 != null) {
            disposable2!!.dispose()
            disposable2 = null
        }
    }

    fun stopUpdateLocation() {
        disposableUpdated?.dispose()
        disposableUpdated = null

        disposable?.dispose()
        disposable = null
    }

    fun checkGPSEnable(isSilent: Boolean): Boolean {
        val locationManager = appContext.getSystemService(LOCATION_SERVICE) as LocationManager

        // Getting GPS status
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        // Getting network status
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGPSEnabled && !isNetworkEnabled) {
            if (!isSilent) {
                showSettingsAlert(context)
            }
            listener!!.gpsEnable(false)
            return false
        }
        listener!!.gpsEnable(true)
        return true
    }

    override fun onReceive(context: Context?, intent: Intent?) {     //disable for mockup gps test
        if (intent != null && intent.action != null && intent.action!!.matches(LocationManager.PROVIDERS_CHANGED_ACTION.toRegex())) {
            if (context != null && listener != null) {
                val isEnable = checkGPSEnable(true)
                if (isFirstReceive && isEnable) {
                    isFirstReceive = false

                    EventBus.getDefault().post(EventBusKey.GPS_ON_OFF.value)
                    /*switch (currentType) {
                        case TYPE_REQUEST_LAST_KNOW:
                            requestLocationLastKnow();
                            break;
                        case TYPE_REQUEST_UPDATE:
                            requestLocationUpdate();
                            break;
                    }*/
                } else {
                    isFirstReceive = true
                }
            }
        }
    }

    private fun checkPermissionLocation(appContext: Context): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            false
        } else true
    }

    interface GPSReceiverListener {
        fun gpsEnable(result: Boolean)

        fun permission(result: Boolean)

        fun location(location: Location)
    }

    companion object {
        private val TYPE_REQUEST_LAST_KNOW = 0
        private val TYPE_REQUEST_UPDATE = 1

        private val TIME_GPS_UPDATE = (1000 * 10).toLong()  //10 secs       --> real every 2s
        private val MIN_DISTANCE_MOVING: Long = 10     //10 meters

        fun showSettingsAlert(context: Context) {
            if (!(context as Activity).isFinishing) {
                DialogUtils.showDialog(context,
                    R.string.permission_location_title,
                    R.string.permission_location_dialog_location_content,
                    R.string.permission_settings,
                    R.string.permission_cancel,
                    object : DialogUtils.DialogListener {
                        override fun positiveClick() {
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            context.startActivityForResult(intent, Constant.REQUEST_CODE_RESULT_LOCATION_ON_OFF)
                        }

                        override fun negativeClick() {}
                    })
            }
        }

        fun checkGPSEnable(context: Context, isSilent: Boolean): Boolean {
            val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

            // Getting GPS status
            val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            // Getting network status
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGPSEnabled && !isNetworkEnabled) {
                if (!isSilent) {
                    showSettingsAlert(context)
                }
                return false
            }
            return true
        }
    }
}
