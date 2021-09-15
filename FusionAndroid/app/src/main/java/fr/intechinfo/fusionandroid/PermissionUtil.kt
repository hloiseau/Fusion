package fr.intechinfo.fusionandroid

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.StrictMode
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.Log

import java.lang.reflect.Method

class PermissionUtil internal constructor() {
    companion object {

        private val TAG = PermissionUtil::class.java!!.getSimpleName()

        val REQUEST_EXTERNAL_STORAGE = 1


        /*<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.BLUETOOTH" />

    <uses-permission android:name="android.permission.CALL_PHONE"/>

    <uses-permission android:name="android.permission.ANSWER_PHONE_CALLS" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />

    <uses-permission android:name="android.permission.SEND_SMS" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>

    <!-- PubNub Dependencies -->
    <!--<uses-permission android:name="android.permission.INTERNET" />-->
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
    <uses-permission android:name="fr.intechinfo.fusionandroid.permission.C2D_MESSAGE" />*/
        fun initPermissions(activity: Activity) {
            // The request code used in ActivityCompat.requestPermissions()
            // and returned in the Activity's onRequestPermissionsResult()
            // int PERMISSION_ALL = 1;
            val PERMISSIONS = arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.WAKE_LOCK, Manifest.permission.ANSWER_PHONE_CALLS,Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.BLUETOOTH,Manifest.permission.CALL_PHONE,Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.VIBRATE)
            if (!hasPermissions(activity, *PERMISSIONS)) {
                showMessageOKCancel(activity,
                "Ces permissions sont obligatoires pour le fonctionnement de l'application.",
                DialogInterface.OnClickListener { dialog, which ->
                    if (!hasPermissions(activity, *PERMISSIONS)) {
                        ActivityCompat.requestPermissions(activity, PERMISSIONS, REQUEST_EXTERNAL_STORAGE)
                    }
                })
            }

            setGAlleryPermissionIntent()
        }

        fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false
                    }
                }
            }
            return true
        }

        private fun showMessageOKCancel(context: Context, message: String, okListener: DialogInterface.OnClickListener) {
            AlertDialog.Builder(context)
                    .setMessage(message)
                    .setPositiveButton("OK", okListener)
                    .setNegativeButton("Annuler", null)
                    .create()
                    .show()
        }

        fun setGAlleryPermissionIntent() {
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    val m = StrictMode::class.java!!.getMethod("disableDeathOnFileUriExposure")
                    m.invoke(null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
}