package com.taru.tools.file

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

import android.content.Context
import android.provider.OpenableColumns
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Created by Niraj on 26-01-2023.
 */
@Singleton
class UriToFile @Inject constructor(@ApplicationContext var context: Context) {

    fun getImageBody(imageUri: Uri): File {
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(
            imageUri,
            "r",
            null
        )
        val file = File(
            context.cacheDir,
            context.contentResolver.getFileName(imageUri)
        )
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }
}

@SuppressLint("Range")
fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val cursor = query(
        uri, null, null,
        null, null
    )
    cursor?.use {
        it.moveToFirst()
        name = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return name
}