package com.danhtran.androidbaseproject.extras.onesignal

/**
 * Created by DanhTran on 7/15/2019.
 */
/*class MyNotificationReceivedHandler : OneSignal.NotificationReceivedHandler {
    override fun notificationReceived(notification: OSNotification?) {
//        JSONObject data = notification.payload.additionalData;
        *//* Notification_Type notification_type;

         if (data != null) {
             int type = data.optInt("type", -1);
             notification_type = Notification_Type.fromValue(type);
             if (notification_type != null) {
                 Logger.d("MyNotificationReceivedHandler: " + notification_type.getValue());
             }

             //only work if app is run on front ground
             //update data
             if (notification_type != null) {
                 switch (notification_type) {
                     case OFFERS:
                         EventBus.getDefault().post(Events.NOTIFICATION_OFFERS.getValue());
                         break;
                     case SOLUTIONS:
                         EventBus.getDefault().post(Events.NOTIFICATION_SOLUTIONS.getValue());
                         break;
                 }
             }
         }*//*
    }
}*/
