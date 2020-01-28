package com.glide.androidbaseproject.extras.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.glide.androidbaseproject.extras.enums.EventBusKey
import com.glide.androidbaseproject.utils.NetworkUtils
import org.greenrobot.eventbus.EventBus

/**
 * Created by DanhTran on 7/23/2019.
 */
class NetworkReceiver(private val context: Context) : BroadcastReceiver() {

    private val appContext: Context

    init {
        this.appContext = context.applicationContext
    }

    fun registerReceiver() {
        appContext.registerReceiver(
            this,
            IntentFilter(CONNECTIVITY_ACTION)
        )      //must register in this to maintain activity + context
    }

    fun unregisterReceiver() {
        appContext.unregisterReceiver(this)      //must register in this to maintain activity + context
    }


    override fun onReceive(context: Context, intent: Intent) {
        if (!isInitialStickyBroadcast && CONNECTIVITY_ACTION == intent.action) {
            val isAvailable = NetworkUtils.isNetworkAvailable(context)
            if (isAvailable) {
                EventBus.getDefault().post(EventBusKey.NETWORK_IS_AVAILABLE.value)
            } else {

            }
        }
    }

    companion object {
        internal val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    }
}
