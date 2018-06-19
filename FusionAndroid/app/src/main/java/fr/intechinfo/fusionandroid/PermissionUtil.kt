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
        private val WRITE_EXTERNAL_PERMISSION_REQUEST_CODE = 1
        val READ_EXTERNAL_PERMISSION_REQUEST_CODE = 2
        val RECORD_AUDIO_PERMISSION_REQUEST_CODE = 3
        val CAMERA_PERMISSION_REQUEST_CODE = 4
        val READ_CONTACTS_REQUEST_CODE = 5
        val SEND_SMS_REQUEST_CODE = 5
        val READ_PHONE_STATE_REQUEST_CODE = 5


        private val PERMISSIONS_WRITE_STORAGE = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        private val PERMISSIONS_READ_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        private val PERMISSIONS_AUDIO = arrayOf(Manifest.permission.RECORD_AUDIO)
        private val PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA)
        private val PERMISSIONS_READ_CONTACTS = arrayOf(Manifest.permission.READ_CONTACTS)
        private val PERMISSIONS_SEND_SMS = arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS)
        private val PERMISSIONS_READ_PHONE_STATE = arrayOf(Manifest.permission.READ_PHONE_STATE)

        /**
         * SAMPLER
         * Checks if the app has permission to write to device storage
         *
         *
         * If the app does not has permission then the user will be prompted to grant permissions
         *
         * @param activity the mContext from which permissions are checked
         */
        fun verifyStoragePermissions(activity: Activity) {
            // Check if we have write permission
            val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_WRITE_STORAGE,
                        WRITE_EXTERNAL_PERMISSION_REQUEST_CODE
                )
            }
        }

        fun verrifyReadStoragePermissions(activity: Activity) {
            val permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_READ_STORAGE[0])
            if (isPermissionDenied(permission)) {
                processPermission(activity, PERMISSIONS_READ_STORAGE[0], PERMISSIONS_READ_STORAGE, READ_EXTERNAL_PERMISSION_REQUEST_CODE)
            }
        }

        fun verrifyWriteStoragePermissions(activity: Activity) {
            val permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_WRITE_STORAGE[0])
            if (isPermissionDenied(permission)) {
                processPermission(activity, PERMISSIONS_WRITE_STORAGE[0], PERMISSIONS_WRITE_STORAGE, WRITE_EXTERNAL_PERMISSION_REQUEST_CODE)
            }
        }

        fun verrifyRecordAudioPermissions(activity: Activity) {
            val permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_AUDIO[0])
            if (isPermissionDenied(permission)) {
                processPermission(activity, PERMISSIONS_AUDIO[0], PERMISSIONS_AUDIO, RECORD_AUDIO_PERMISSION_REQUEST_CODE)
            }
        }

        fun verrifyCameraPermissions(activity: Activity) {
            val permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_CAMERA[0])
            if (isPermissionDenied(permission)) {
                processPermission(activity, PERMISSIONS_CAMERA[0], PERMISSIONS_CAMERA, CAMERA_PERMISSION_REQUEST_CODE)
            }
        }

        fun verrifyContactsPermissions(activity: Activity) {
            val permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_READ_CONTACTS[0])
            if (isPermissionDenied(permission)) {
                processPermission(activity, PERMISSIONS_READ_CONTACTS[0], PERMISSIONS_READ_CONTACTS, READ_CONTACTS_REQUEST_CODE)
            }
        }


        fun verrifyPhoneStatePermissions(activity: Activity) {
            val permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_READ_PHONE_STATE[0])
            if (isPermissionDenied(permission)) {
                processPermission(activity, PERMISSIONS_READ_PHONE_STATE[0], PERMISSIONS_READ_PHONE_STATE, READ_PHONE_STATE_REQUEST_CODE)
            }
        }

        fun verrifySMSPermissions(activity: Activity) {
            val permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_SEND_SMS[0])
            if (isPermissionDenied(permission)) {
                processPermission(activity, PERMISSIONS_SEND_SMS[0], PERMISSIONS_SEND_SMS, SEND_SMS_REQUEST_CODE)
            }
        }

        private fun isPermissionDenied(permission: Int): Boolean {
            return permission != PackageManager.PERMISSION_GRANTED
        }

        private fun processPermission(activity: Activity, permissionManifest: String, permissions: Array<String>, requestCode: Int) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionManifest)) {
                Log.e(TAG, "shouldShowRequestPermissionRationale is invoked $permissionManifest")
            } else {
                ActivityCompat.requestPermissions(activity, permissions, requestCode)
                Log.e(TAG, "requestPermissions is invoked $permissionManifest")
            }
        }

        /**
         * SAMPLER
         * Request the permissions you need
         * If your app doesn't already have the permission it needs, the app must call one of the requestPermissions() methods to request the appropriate permissions.
         * Your app passes the permissions it wants, and also an integer request code that you specify to identify this permission request.
         * This method functions asynchronously: it returns right away, and after the user responds to the dialog box,
         * the system calls the app's callback method with the results, passing the same request code that the app passed to requestPermissions().
         * @param activity - you mContext
         */
        fun verifyShowRequestPrompt(activity: Activity) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.e(TAG, "shouldShowRequestPermissionRationale is invoked")
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.CAMERA),
                        WRITE_EXTERNAL_PERMISSION_REQUEST_CODE)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        /**
         * ABOVE CODES is not used
         * @param activity
         */

        fun initPermissions(activity: Activity) {
            // The request code used in ActivityCompat.requestPermissions()
            // and returned in the Activity's onRequestPermissionsResult()
            // int PERMISSION_ALL = 1;
            val PERMISSIONS = arrayOf(Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.VIBRATE)
            if (!hasPermissions(activity, *PERMISSIONS)) {
                showMessageOKCancel(activity,
                "These permissions are mandatory for the application. Please allow access.",
                DialogInterface.OnClickListener { dialog, which ->
                    if (!hasPermissions(activity, *PERMISSIONS)) {
                        ActivityCompat.requestPermissions(activity, PERMISSIONS, REQUEST_EXTERNAL_STORAGE)
                    }
                })
            }

            setGAlleryPermissionIntent()
        }

        fun initPermissions(context: Context) {
            // The request code used in ActivityCompat.requestPermissions()
            // and returned in the Activity's onRequestPermissionsResult()
            // int PERMISSION_ALL = 1;
            val PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA)
            if (!hasPermissions(context, *PERMISSIONS)) {
                showMessageOKCancel(context, "These permissions are mandatory for the application. Please allow access.",
                        DialogInterface.OnClickListener { dialog, which ->
                            if (!hasPermissions(context, *PERMISSIONS)) {
                                ActivityCompat.requestPermissions(context as Activity, PERMISSIONS, REQUEST_EXTERNAL_STORAGE)
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
                    .setNegativeButton("Cancel", null)
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