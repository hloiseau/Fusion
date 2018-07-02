package fr.intechinfo.fusionandroid

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Environment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Environment.getExternalStorageDirectory
import android.os.Handler
import android.os.Looper
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.coroutines.experimental.coroutineContext


class CallbackRetroFit(val fileName:String, val context:Context) : Callback<ResponseBody> {
    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
        System.out.println(t.toString())
    }

    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {

        try {
            val path = getExternalStorageDirectory()
            val file = File(path, fileName)
            val fileOutputStream = FileOutputStream(file)
            org.apache.commons.io.IOUtils.write(response!!.body()!!.bytes(), fileOutputStream)

        } catch (e: IOException) {
            Log.e("Callback", "Error while writing file!")
            Log.e("Callback", e.toString())
        }

}
}