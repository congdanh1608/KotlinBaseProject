package com.danhtran.androidbaseproject.utils

import android.annotation.SuppressLint
import android.content.Context
import com.danhtran.androidbaseproject.extras.listener.SingleResultListener

import java.io.File

/**
 * Created by danhtran on 1/4/2019.
 */
object FileUtils {
    private val IMAGE_QUALITY = 80
    private val IMAGE_SIZE = 1080

    /**
     * Resize image file.
     * The extension will follow by the source image
     * Filename should be provided without extension
     *
     *
     * implementation 'com.github.hkk595:Resizer:v1.5'
     *
     * @param imageFile context
     * @param generalId generated UUID
     * @param imageFile image file
     */
    @SuppressLint("CheckResult")
    fun convertImageToSmallSize(
        context: Context,
        generalId: String,
        imageFile: File,
        listener: SingleResultListener<File>
    ) {
        val imageNameWithoutExt = generalId + "_small"

        /*new Resizer(context)
                .setTargetLength(IMAGE_SIZE)
                .setQuality(IMAGE_QUALITY)
                .setOutputFormat(Bitmap.CompressFormat.JPEG)
                .setOutputFilename(imageNameWithoutExt)
                .setOutputDirPath(context.getCacheDir().toString())
                .setSourceImage(imageFile)
                .getResizedFileAsFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        listener.onSuccess(file);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });*/
    }
}
