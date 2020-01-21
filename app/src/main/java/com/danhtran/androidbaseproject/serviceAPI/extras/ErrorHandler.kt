package com.danhtran.androidbaseproject.serviceAPI.extras

import android.text.TextUtils
import android.view.View
import com.danhtran.androidbaseproject.serviceAPI.model.Error
import com.danhtran.androidbaseproject.serviceAPI.model.ResponseModel
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.danhtran.androidbaseproject.utils.SnackBarUtils
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.util.*

/**
 * Created by DanhTran on 5/31/2019.
 */
object ErrorHandler {
    /**
     * handle error and show by snack bar or toast
     *
     * @param throwable throwable
     * @param activity  BaseAppCompatActivity
     */
    fun showActivityError(throwable: Throwable, activity: BaseAppCompatActivity) {
        if (throwable is HttpException) {
            try {
                val errorBody = Objects.requireNonNull<ResponseBody>(throwable.response()!!.errorBody()).string()
                if (!TextUtils.isEmpty(errorBody)) {
                    val gson = Gson()
                    val responseModel = gson.fromJson<ResponseModel<*>>(errorBody, ResponseModel<*>::class.java)
                    if (responseModel != null && responseModel!!.errors != null) {
                        val errors = responseModel!!.errors
                        val error = errors!!.get(0) as Error
                        when (error.errorCode) {
                            401   //un authentication
                            -> {
                            }
                            400 -> showError(activity, responseModel!!.getData() as String)
                            500 -> showError(activity, responseModel!!.getData() as String)
                            else -> showError(activity, responseModel!!.getData() as String)
                        }//                                activity.startActivityAsRoot(AuthenActivity.class.getName(), null);
                    }
                } else {
                    showError(activity, throwable.message)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (jsonParseException: JsonParseException) {
                jsonParseException.printStackTrace()
            }

        } else {
            showError(activity, throwable.message)
        }
        Logger.e(throwable, activity.javaClass.simpleName)
    }

    /**
     * handle error and show by snack bar or toast
     *
     * @param throwable throwable
     * @param activity  BaseAppCompatActivity
     * @param view      Dialog view
     */
    fun showDialogError(throwable: Throwable, activity: BaseAppCompatActivity, view: View) {
        if (throwable is HttpException) {
            try {
                val errorBody = Objects.requireNonNull<ResponseBody>(throwable.response()!!.errorBody()).string()
                if (!TextUtils.isEmpty(errorBody)) {
                    val gson = Gson()
                    val responseModel = gson.fromJson<ResponseModel<*>>(errorBody, ResponseModel<*>::class.java)
                    if (responseModel != null && responseModel!!.errors != null) {
                        val errors = responseModel!!.errors
                        val error = errors!!.get(0) as Error
                        when (error.errorCode) {
                            401   //un authentication
                            -> {
                            }
                            400 -> showError(view, responseModel!!.getData() as String)
                            500 -> showError(view, responseModel!!.getData() as String)
                            else -> showError(view, responseModel!!.getData() as String)
                        }//activity.startActivityAsRoot(AuthenActivity.class.getName(), null);
                    }
                } else {
                    showError(view, throwable.message)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (jsonParseException: JsonParseException) {
                jsonParseException.printStackTrace()
            }

        } else {
            showError(view, throwable.message)
        }
        Logger.e(throwable, view.javaClass.simpleName)
    }

    private fun showError(view: View, message: String?) {
        message?.let { SnackBarUtils.showGeneralError(view, it) }
    }

    private fun showError(activity: BaseAppCompatActivity, message: String) {
        activity.rootView?.let { SnackBarUtils.showGeneralError(it, message) }
    }
}
