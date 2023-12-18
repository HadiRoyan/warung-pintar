package com.capstone.warungpintar.utils

import android.app.Activity
import android.net.Uri
import com.capstone.warungpintar.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object ImageUtils {

    fun uriToFile(imageUri: Uri, activity: Activity): File {
        val myFile = getOutputDirectory(activity)
        val inputStream = activity.contentResolver.openInputStream(imageUri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(
            buffer,
            0,
            length
        )
        outputStream.close()
        inputStream.close()
        return myFile
    }

    fun getOutputDirectory(activity: Activity): File {
        val mediaDir = activity.externalMediaDirs.firstOrNull()?.let {
            File(it, activity.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return mediaDir ?: activity.filesDir
    }
}

