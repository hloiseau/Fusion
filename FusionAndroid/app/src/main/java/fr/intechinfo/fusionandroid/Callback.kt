package fr.intechinfo.fusionandroid

import android.content.Context
import com.pubnub.api.Callback
import org.json.JSONException
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.util.Log
import org.json.JSONObject



class Callback(val ctx: Context): Callback() {

    override fun successCallback(channel: String?, message: Any?) {
        Log.d("MA-success", "MESSAGE: " + message!!.toString())
        if (message !is JSONObject) return  // Ignore if not JSONObject
        val jsonMsg = message as JSONObject?
        try {
            Rtc.instance.initRtcAudio(ctx)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}