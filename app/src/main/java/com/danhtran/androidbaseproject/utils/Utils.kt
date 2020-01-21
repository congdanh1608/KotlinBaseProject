package com.danhtran.androidbaseproject.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.util.Base64
import com.danhtran.androidbaseproject.MyApplication
import com.orhanobut.logger.Logger

import java.security.MessageDigest

/**
 * Created by danhtran on 17/06/2017.
 */

object Utils {

    //get url of this app with package name
    private// getPackageName() from Context or Activity object
    val urlApp: String
        get() {
            val appPackageName = MyApplication.instance().packageName
            return "https://play.google.com/store/apps/details?id=$appPackageName"
        }

    /**
     * navigating user to app settings
     *
     * @param activity activity
     * @param code     request code
     */
    fun openSettings(activity: Activity, code: Int) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", MyApplication.instance().packageName, null)
        intent.data = uri
        activity.startActivityForResult(intent, code)
    }

    /**
     * open camera
     *
     * @param activity activity
     * @param code     request code
     */
    fun openCamera(activity: Activity, code: Int) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(intent, code)
    }

    /**
     * open URL
     *
     * @param activity activity
     * @param url      url
     */
    fun openUrl(activity: Activity, url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        activity.startActivity(i)
    }

    /**
     * Open play store
     *
     * @param activity activity
     */
    fun goToPlayStore(activity: Activity) {
        val appPackageName = MyApplication.instance().packageName // getPackageName() from Context or Activity object
        try {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (anfe: android.content.ActivityNotFoundException) {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlApp)))
        }

    }

    /*public static void shareAppByMsg(Activity activity) {
        String message = getUrlApp();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        activity.startActivity(Intent.createChooser(share, activity.getString(R.string.share_app)));
    }

    public static void sendEmailTo(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{activity.getString(R.string.email_developer)});
        intent.putExtra(Intent.EXTRA_SUBJECT, "[" + activity.getString(R.string.app_name) + "] "
                + activity.getString(R.string.feedback));
        intent.putExtra(Intent.EXTRA_TEXT, "");

        activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.send_email)));
    }*/

    fun generalSHAKey(context: Context) {
        try {
            //by facebook sdk
            /*FacebookSdk.sdkInitialize(getApplicationContext());
            Log.d("AppLog", "key:" + FacebookSdk.getApplicationSignature(this));*/
            //or without facebook sdk
            val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Logger.d("SHA KEY = $hashKey")
            }
        } catch (e: Exception) {
            Logger.e(e.message)
        }

    }
}
