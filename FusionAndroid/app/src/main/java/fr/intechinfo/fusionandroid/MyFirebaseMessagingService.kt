package fr.intechinfo.fusionandroid

import android.telephony.SmsManager
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


import android.content.ContentValues.TAG
import android.content.Context
import android.media.AudioManager
import okhttp3.Callback
import android.media.RingtoneManager
import android.os.Build
import android.os.Vibrator
import android.support.annotation.RequiresApi
import android.telecom.TelecomManager
import org.webrtc.SessionDescription
import okhttp3.ResponseBody
import org.webrtc.IceCandidate
import org.webrtc.SdpObserver
import android.widget.Toast
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.EditText
import android.os.Environment
import android.os.Looper
import android.provider.DocumentsContract
import android.view.Display
import retrofit2.Call
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Thread.sleep


class MyFirebaseMessagingService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val data = remoteMessage!!.data
        val strTitle = data["title"]
        val message = data["text"]
        val type = data["type"]
        Log.d("MyFireBasemessaging", "onMessageReceived:  Message Received: \nTitle: $strTitle\nMessage: $message")
        if (type == "sms") {
            sendSMS(strTitle, message)
        } else if (type == "foundPhone") {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            val r = RingtoneManager.getRingtone(applicationContext, notification)
            r.play()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(1000)
        } else if (type == "file") {
            //DownloadFile(message)
        } else if (type == "takecall") {
            val tm = this.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
            checkSelfPermission("android.permission.ANSWER_PHONE_CALLS")
            isTaked = true
            tm.acceptRingingCall()
            isTaked = false
        }
        else if (type == "URL") {
            Log.d("fireURLLL", "onMessageReceived:  Message Received: \nMessage: $message")
            LauchURL(message)
        }
        when (type) {
            "sms" -> sendSMS(strTitle, message)
            "foundPhone" -> {
                val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                val r = RingtoneManager.getRingtone(applicationContext, notification)
                r.play()
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                v.vibrate(1000)
            }
            "file" -> {
                DownloadFile(message)
            }
            "takecall" -> {
                val tm = this.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
                checkSelfPermission("android.permission.ANSWER_PHONE_CALLS")
                isTaked = true
                tm.acceptRingingCall()
                isTaked = false
            }
            "URL" -> {
                Log.d("fireURLLL", "onMessageReceived:  Message Received: \nMessage: $message")
                LaunchURL(message)
            }


        //SyncData()
        }


        //SyncData()
    }

    private fun sendSMS(phoneNumber: String?, messageBody: String?) {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, messageBody, null, null)
    }

    private fun DownloadFile(fileName: String?){
        Log.d("MyDownloadFire", "onMessageReceived:  Message Received: \nTitle:")
        val retrofitAPI = HttpExecute.BuildAPI()
        val call  = HttpExecute(retrofitAPI.downloadFileWithDynamicUrlSync(fileName!!))._call as Call<ResponseBody>
        call.enqueue(CallbackRetroFit(fileName))
    }

    private fun LauchURL(url: String?) {
            val uris = Uri.parse(url)
            val browserIntent = Intent(Intent.ACTION_VIEW, uris)
            startActivity(browserIntent)
    private fun LaunchURL(url: String?) {

        val thread = object : Thread() {
            override fun run() {
                try {
                    Looper.prepare()
                    Toast.makeText(application, "Url Received", Toast.LENGTH_SHORT).show()
                    Thread.sleep(5000)
                    val uris = Uri.parse(url)
                    val browserIntent = Intent(Intent.ACTION_VIEW, uris)
                    startActivity(browserIntent)

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }

        thread.start()

    }

    /*private fun SyncData() {
        val retrofitAPI = HttpExecute.BuildAPI()
        val cc = ContentCollector()
        val contactLs = ContactsList()
        contactLs.SetContact(cc.GetContacts(this))
        Log.d("SYNC", "Name : " + contactLs.GetContact()!![1].GetName() + " Number : " + contactLs.GetContact()!![1].GetNumber())
        val SMSLs = SMSList()
        SMSLs.SetSMS(cc.GetSMS(this))
        Log.d("SYNC", "Address : " + SMSLs.GetSMS()!![3].GetAddress() + " Message : " + SMSLs.GetSMS()!![3].GetBody() + " Date : " + SMSLs.GetSMS()!![3].GetDate() + " Type : " + SMSLs.GetSMS()!![3].GetType())

        HttpExecute(retrofitAPI.CreateContacts(contactLs)).start()
        HttpExecute(retrofitAPI.CreateSMS(SMSLs)).start()
    }

    /*private fun rtcSignaling(type: String, message: String, rtc: Rtc?){
        if(rtc?.peerConnection == null){
            rtc?.initRtcAudio(this.applicationContext)
        }
        if(type == "sdp"){
            rtc?.peerConnection?.setRemoteDescription(RtcSdpObserver(), SessionDescription(SessionDescription.Type.OFFER, message))
        }
        else{
            rtc?.peerConnection?.addIceCandidate(IceCandidate("",0,message))
        }
    }*/

    companion object {
        var isTaked = false
    }
}