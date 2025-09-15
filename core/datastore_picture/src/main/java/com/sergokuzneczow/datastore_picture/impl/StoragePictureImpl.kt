package com.sergokuzneczow.datastore_picture.impl

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.sergokuzneczow.datastore_picture.api.StoragePictureApi
import jakarta.inject.Inject
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.util.UUID

public class StoragePictureImpl @Inject constructor(
    private val context: Context
) : StoragePictureApi {
    private companion object {

        const val APPLICATION_FOLDER_NAME: String = "Pixels"
    }

    override fun savePictureBitmapToPictureDir(bitmap: Bitmap, onStart: () -> Unit, onSuccess: (uri: Uri) -> Unit, onFailure: (e: Exception) -> Unit) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> saveWhenApiMoreOrEqualsQ(bitmap, onStart, onSuccess, onFailure)
//            else -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            else -> throw IllegalStateException("Application can't saved picture when device runs on api<29.")
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveWhenApiMoreOrEqualsQ(bitmap: Bitmap, onStart: () -> Unit, onSuccess: (uri: Uri) -> Unit, onFailure: (e: Exception) -> Unit) {
        onStart.invoke()
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, UUID.randomUUID().toString())
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/$APPLICATION_FOLDER_NAME")
        }
        val collectionUri: Uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val resolver: ContentResolver = context.contentResolver
        val uri: Uri? = resolver.insert(collectionUri, contentValues)
        val outputStream: OutputStream? = uri?.let { resolver.openOutputStream(it) }
        try {
            outputStream?.apply {
                write(bitmapToByteArray(bitmap))
                onSuccess.invoke(uri)
            }
        } catch (e: Exception) {
            onFailure.invoke(e)
            outputStream?.close()
        } finally {
            outputStream?.close()
        }
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(
            Bitmap.CompressFormat.PNG,
            100,
            stream
        )
        return stream.toByteArray()
    }
}