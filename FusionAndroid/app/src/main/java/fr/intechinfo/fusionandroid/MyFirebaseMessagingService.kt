package fr.intechinfo.fusionandroid

import android.app.*
import android.telephony.SmsManager
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


import android.content.ContentValues.TAG
import android.content.Context
import android.media.AudioManager
import okhttp3.Callback
import android.media.RingtoneManager
import android.support.annotation.RequiresApi
import android.telecom.TelecomManager
import okhttp3.ResponseBody
import android.widget.Toast
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.*
import android.view.View
import android.widget.EditText
import android.os.Environment
import android.os.Looper
import android.provider.DocumentsContract
import android.support.v4.app.NotificationCompat
import android.view.Display
import android.view.Window
import android.view.WindowManager
import org.webrtc.*
import retrofit2.Call
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Thread.sleep


class MyFirebaseMessagingService : FirebaseMessagingService() {

    lateinit var mgr : MediaProjectionManager
    lateinit var wmgr : WindowManager
    lateinit var handler : Handler

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val data = remoteMessage!!.data
        val strTitle = data["title"]
        val message = data["text"]
        val type = data["type"]
        val handlerThread = HandlerThread(javaClass.simpleName, android.os.Process.THREAD_PRIORITY_BACKGROUND)
        Log.d("MyFireBasemessaging", "onMessageReceived:  Message Received: \nTitle: $strTitle\nMessage: $message")
        when (type) {
            "sms" -> {
                val mainHandler = Handler(Looper.getMainLooper())
                val myRunnable = object : Runnable {
                    override fun run() {
                        Toast.makeText(applicationContext, "Envoie d'un SMS...", Toast.LENGTH_SHORT).show()
                    }
                }
                mainHandler.post(myRunnable)
                sendSMS(strTitle, message)
            }
            "foundPhone" -> {
                val mainHandler = Handler(Looper.getMainLooper())
                val myRunnable = object : Runnable {
                    override fun run() {
                        Toast.makeText(applicationContext, "Lancement de la sonnerie...", Toast.LENGTH_SHORT).show()
                    }
                }
                mainHandler.post(myRunnable)
                val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                val r = RingtoneManager.getRingtone(applicationContext, notification)
                r.play()
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                v.vibrate(1000)
            }
            "file" -> {
                val mainHandler = Handler(Looper.getMainLooper())
                val myRunnable = object : Runnable {
                    override fun run() {
                        Toast.makeText(applicationContext, "Récéption d'un fichier...", Toast.LENGTH_SHORT).show()
                    }
                }
                mainHandler.post(myRunnable)
                DownloadFile(message)
            }
            "takecall" -> {
                val mainHandler = Handler(Looper.getMainLooper())
                val myRunnable = object : Runnable {
                    override fun run() {
                        Toast.makeText(applicationContext, "Prise de l'appel...", Toast.LENGTH_SHORT).show()
                    }
                }
                mainHandler.post(myRunnable)
                val tm = this.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
                checkSelfPermission("android.permission.ANSWER_PHONE_CALLS")
                isTaked = true
                tm.acceptRingingCall()
                isTaked = false
            }
            "URL" -> {
                val mainHandler = Handler(Looper.getMainLooper())
                val myRunnable = object : Runnable {
                    override fun run() {
                        Toast.makeText(applicationContext, "Récéption d'un lien...", Toast.LENGTH_SHORT).show()
                    }
                }
                mainHandler.post(myRunnable)
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
        call.enqueue(CallbackRetroFit(fileName, applicationContext))

        val path = Environment.getExternalStorageDirectory()
        val file = File(path, fileName)
        val intent = Intent()
        intent.action = android.content.Intent.ACTION_VIEW
        intent.setDataAndType(Uri.fromFile(file), "image/*")
        val pIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val notification = NotificationCompat.Builder(applicationContext, "OutputStream")
                .setContentTitle("Fichier reçu")
                .setContentText("Toucher ici pour l'ouvrir")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager)
        }
        notificationManager.notify(0, notification.build())
    }

    private fun LaunchURL(url: String?) {

        val thread = object : Thread() {
            override fun run() {
                try {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(manager: NotificationManager){
        val adminChannelName = getString(R.string.common_google_play_services_notification_channel_name);
        val adminChannelDescription = getString(R.string.common_google_play_services_notification_ticker);

        val adminChannel = NotificationChannel("OutputStream", adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription)
        adminChannel.enableLights(true)
        adminChannel.setLightColor(Color.CYAN)
        adminChannel.enableVibration(true)
        adminChannel.setBypassDnd(true)
        adminChannel.setShowBadge(true)

        manager.createNotificationChannel(adminChannel);

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
    }
*/*/
    companion object {
        var isTaked = false
    }
}