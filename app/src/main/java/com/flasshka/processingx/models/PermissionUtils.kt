package com.flasshka.processingx.models

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    fun haveMemoryPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            (ContextCompat.checkSelfPermission(context, "WRITE_EXTERNAL_STORAGE")
                    == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(context, "READ_EXTERNAL_STORAGE")
                    == PackageManager.PERMISSION_GRANTED)
        }
    }

    fun requestPermissions(activity: ComponentActivity, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.setData(Uri.parse(String.format("package:%s", activity.packageName)))
                activity.startActivityForResult(intent, requestCode)
            } catch (e: Exception) {
                val intent = Intent()
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                activity.startActivityForResult(intent, requestCode)
            }
        } else {
            ActivityCompat.requestPermissions(
                activity, arrayOf("WRITE_EXTERNAL_STORAGE", "READ_EXTERNAL_STORAGE"),
                requestCode
            )
        }
    }
}