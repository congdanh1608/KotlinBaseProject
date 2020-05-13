package com.danhtran.androidbaseproject.services.extras

import android.content.Context
import android.widget.Toast
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.exception.ApolloNetworkException
import com.danhtran.androidbaseproject.MyApplication
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.services.model.ResponseModel
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.danhtran.androidbaseproject.utils.NetworkUtils
import com.danhtran.androidbaseproject.utils.SnackBarUtils
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
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
    fun showError(throwable: Throwable, context: Context?) {
        context?.let {
            when (throwable) {
                //read api http exception from server
                is HttpException -> {
                    try {
                        //read error body
                        throwable.response()?.errorBody()?.let {
                            val errorBody = it.string()
                            if (JsonParser.isJsonValid(errorBody)) {
                                val responseModel = Gson().fromJson(errorBody, ResponseModel::class.java)
                                //read error from response model
                                responseModel?.errors?.let { errors ->
                                    val error = errors[0]
                                    showNotifyByErrorCode(context, error.errorMessage, error.errorCode ?: 0)
                                    return
                                }
                            }
                        }

                        //read error code without error body
                        throwable.response()?.code()?.let {
                            showNotifyByErrorCode(context, throwable.message, it)
                            return
                        }

                        //show unknown error
                        showUnKnownError(context)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                //apollo graphql error from server
                is ApolloHttpException -> {
                    val code = throwable.code()
                    showNotifyByErrorCode(context, throwable.message(), code)
                }
                //error on connection
                is UnknownHostException, is ApolloNetworkException -> {
                    if (!NetworkUtils.isInternetAvailable(context)) {
                        showNotify(context, R.string.notify_network_error)
                    } else {
                        showNotify(context, R.string.notify_connect_error)
                    }
                }
                is SocketTimeoutException -> {
                    showNotify(context, R.string.notify_connect_timeout)
                }
                //error on parsing model
                is JsonSyntaxException -> {
                    showNotify(context, R.string.notify_response_error)
                }
                //unknown error
                else -> {
                    showUnKnownError(context)
                }
            }
            //log
            context.let {
                Logger.e(throwable, context.javaClass.name)
            }
        }
    }

    private fun showNotifyByErrorCode(context: Context, message: String?, errorCode: Int) {
        when (errorCode) {
            401 -> {
                //handle un-authentication here

                //clear session
                //MyApplication.instance().clearLoginSession();
                //start authentication screen
                /*Bundle bundle = new Bundle();
                                        bundle.putString(SecondaryActivity.KEY_FRAGMENT_TAG, SignInFragment.class.getName());
                                        if (context instanceof BaseAppCompatActivity) {
                                            BaseAppCompatActivity activity = (BaseAppCompatActivity) context;
                                            activity.startActivity(SecondaryActivity.class.getName(), bundle);
                                        }*/
            }
            400, 500 -> {
                showNotify(context, message)
            }
            404 -> {
                showNotify(context, R.string.notify_could_not_resolve_host)
            }
            else -> {
                showUnKnownError(context)
            }
        }
    }

    private fun showUnKnownError(context: Context) {
        showNotify(context, R.string.notify_unknown_error)
    }

    private fun showNotify(context: Context, message: String?) {
        when {
            context is BaseAppCompatActivity -> {
                showSnackNotify(context, message)
            }
            MyApplication.instance().applicationContext != null -> {
                val applicationContext = MyApplication.instance().applicationContext
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showNotify(context: Context, messageId: Int) {
        showNotify(context, context.getString(messageId))
    }

    private fun showSnackNotify(activity: BaseAppCompatActivity, message: String?) {
        SnackBarUtils.showGeneralNotify(activity, message!!)
    }
}
