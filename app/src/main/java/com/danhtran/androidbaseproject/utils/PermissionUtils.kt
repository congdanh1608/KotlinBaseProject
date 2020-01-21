package com.danhtran.androidbaseproject.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.extras.Constant
import com.danhtran.androidbaseproject.extras.enums.EventBusKey
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.greenrobot.eventbus.EventBus

/**
 * Created by DanhTran on 5/8/2019.
 */
object PermissionUtils {
    val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    val PERMISSION_GRANTED = 0
    val PERMISSION_DENIED = 1
    val PERMISSION_BLOCK_OR_NEVER_ASKED = 2

    /**
     * ic_check_green permission for app
     *
     * @param activity
     * @param permissions
     * @return
     */
    private fun getPermissionsStatus(activity: Activity?, vararg permissions: String): Int {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity != null && permissions != null) {
                for (permission in permissions) {
                    if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                        return if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                            PERMISSION_BLOCK_OR_NEVER_ASKED
                        } else PERMISSION_DENIED
                    }
                }
                return PERMISSION_GRANTED
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return PERMISSION_GRANTED
    }

    fun getLocationPermissionsStatus(activity: Activity): Int {
        return getPermissionsStatus(activity, *PERMISSIONS)
    }

    fun requestLocationPermission(activity: Activity, listener: RequestPermissionListener) {
        Dexter.withActivity(activity)
            .withPermissions(*PermissionUtils.PERMISSIONS)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        listener.areAllPermissionGranted()
                        EventBus.getDefault().post(EventBusKey.UPDATE_ACCOUNT_SCREEN.value)
                    }
                    if (report.isAnyPermissionPermanentlyDenied || !report.deniedPermissionResponses.isEmpty()) {
                        // show alert dialog navigating to Settings
                        showSettingsDialog(activity, listener)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .withErrorListener { Toast.makeText(activity, "Error occurred! ", Toast.LENGTH_SHORT).show() }
            .onSameThread().check()
    }

    private fun showSettingsDialog(activity: Activity, listener: RequestPermissionListener) {
        DialogUtils.showDialog(activity,
            R.string.permission_location_title,
            R.string.permission_location_dialog_location_content,
            R.string.permission_location_positive_btn, R.string.permission_location_nagative_btn,
            object : DialogUtils.DialogListener {
                override fun positiveClick() {
                    Utils.openSettings(activity, Constant.REQUEST_CODE_RESULT_LOCATION_PERMISSION)
                }

                override fun negativeClick() {
                    listener.isAnyPermissionDenied()
                }
            })
    }

    interface RequestPermissionListener {
        fun areAllPermissionGranted()

        fun isAnyPermissionDenied()
    }
}
