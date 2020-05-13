package com.danhtran.androidbaseproject.extras.onesignal

import android.content.Context
import android.content.Intent
import com.danhtran.androidbaseproject.MyApplication
import com.danhtran.androidbaseproject.ui.activity.main.MainActivity

/**
 * Created by DanhTran on 7/15/2019.
 */

/*class MyNotificationOpenedHandler : OneSignal.NotificationOpenedHandler {
    companion object {
        val KEY_NOTIFICATION_DATA = "KEY_NOTIFICATION_DATA"
        val KEY_NOTIFICATION_ACTION = "KEY_NOTIFICATION_ACTION"
    }

    override fun notificationOpened(result: OSNotificationOpenResult?) {
        val actionType = result!!.action.type
        //        JSONObject data = result.notification.payload.additionalData;
*//*Notification_Type notification_type = null;

        if (data != null) {
            int type = data.optInt("type", -1);
            notification_type = Notification_Type.fromValue(type);
            if (notification_type != null) {
                Logger.d("MyNotificationOpenedHandler: " + notification_type.getValue());
            }
        }

        if (actionType == OSNotificationAction.ActionType.ActionTaken) {
//            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);

            // The following can be used to open an Activity of your choice.
            // Replace - getApplicationContext() - with any Android Context.
            // Intent intent = new Intent(getApplicationContext(), YourActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            // startActivity(intent);
        }*//*
        val context: Context = MyApplication.instance().applicationContext
        val intent = Intent(context, MainActivity::class.java)
//        intent.putExtra(KEY_NOTIFICATION_DATA, notification_type);
        //        intent.putExtra(KEY_NOTIFICATION_DATA, notification_type);
        intent.putExtra(MyNotificationOpenedHandler.KEY_NOTIFICATION_ACTION, actionType)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}*/
