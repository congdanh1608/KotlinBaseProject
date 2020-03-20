package com.danhtran.androidbaseproject.serviceAPI.extras

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.danhtran.androidbaseproject.MyApplication
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.serviceAPI.model.ResponseModel
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.danhtran.androidbaseproject.utils.SnackBarUtils
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by DanhTran on 5/31/2019.
 */
object ErrorHandler {
    /**
     * handle error and show by snack bar or toast
     *
     * @param throwable throwable
     * @param context   prefer BaseActivity than Context
     */
    fun showError(throwable: Throwable, context: Context) {
        if (throwable is HttpException) {
            try {
                var errorBody: String? = null
                if (throwable.response() != null) {
                    val response = throwable.response()
                    if (response != null && response.errorBody() != null) {
                        errorBody = throwable.response()!!.errorBody()!!.string()
                    }
                }
                if (!TextUtils.isEmpty(errorBody) && JsonParser.isJsonValid(errorBody)) {
                    val responseModel = Gson().fromJson(errorBody, ResponseModel::class.java)
                    if (responseModel?.errors != null) {
                        val errors = responseModel.errors
                        val error = errors!![0]
                        showNotifyByErrorCode(context, error.errorMessage, error.errorCode!!)
                    } else {
                        showNotify(context, throwable.message)
                    }
                } else if (throwable.response() != null) {
                    showNotifyByErrorCode(context, throwable.message, throwable.code())
                } else {
                    showUnKnowError(context, throwable.message)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } else if (throwable is SocketTimeoutException || throwable is UnknownHostException) {
            showNotify(context, R.string.notify_network_error)
        } else {
            showUnKnowErrorWithoutTracking(context)
        }
        Logger.e(throwable, context.javaClass.simpleName)
    }

    private fun showNotifyByErrorCode(context: Context, message: String?, errorCode: Int) {
        when (errorCode) {
            401   //un authentication
            -> {
            }
            400, 500 -> showNotify(context, message)
            404 -> showNotify(context, R.string.notify_could_not_resolve_host)
            else -> showUnKnowError(context, message)
        }//clear session
        //                MyApplication.instance().clearLoginSession();
        //start authentication screen
        /*Bundle bundle = new Bundle();
                                bundle.putString(SecondaryActivity.KEY_FRAGMENT_TAG, SignInFragment.class.getName());
                                if (context instanceof BaseAppCompatActivity) {
                                    BaseAppCompatActivity activity = (BaseAppCompatActivity) context;
                                    activity.startActivity(SecondaryActivity.class.getName(), bundle);
                                }*/
    }

    private fun showNotify(activity: BaseAppCompatActivity, message: String?) {
        SnackBarUtils.showGeneralNotify(activity, message!!)
    }

    private fun showNotify(context: Context, message: String?) {
        if (context is BaseAppCompatActivity) {
            showNotify(context, message)
        } else {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun showNotify(context: Context?, idMessage: Int) {
        if (context != null) {
            showNotify(context, context.getString(idMessage))
        } else {
            Toast.makeText(
                MyApplication.instance(),
                MyApplication.instance().getString(idMessage),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    interface ErrorHandlerListener {
        fun success()

        fun failure()
    }

    private fun showUnKnowError(context: Context, trackingMsg: String?) {
        showNotify(context, R.string.notify_unknown_error)
    }

    private fun showUnKnowErrorWithoutTracking(context: Context) {
        showNotify(context, R.string.notify_unknown_error)
    }
}
