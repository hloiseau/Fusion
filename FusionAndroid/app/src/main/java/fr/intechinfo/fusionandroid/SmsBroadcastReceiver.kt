package fr.intechinfo.fusionandroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log

import java.util.ArrayList

/**
 * A broadcast receiver who listens for incoming SMS
 */

class SmsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "SMSBroadcastReceiver.onReceive(Context, Intent)")
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            var smsSender: String
            var smsBody = ""
            val smsList = ArrayList<SMS>()
            var smsDate: Long
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsSender = smsMessage.displayOriginatingAddress
                smsBody += smsMessage.messageBody
                smsDate = smsMessage.timestampMillis
                smsList.add(SMS(smsSender, smsBody, smsDate.toString(), "1"))
                val sb = StringBuilder()
                sb.append("Sender : $smsSender\n")
                sb.append("Body : $smsBody\n")
                sb.append("Date : $smsDate\n")
                Log.d(TAG, sb.toString())
            }
            val list = SMSList()
            list.SetSMS(smsList)
            val retrofitAPI = HttpExecute.BuildAPI()
            HttpExecute(retrofitAPI.CreateSMS(list)).start()
        }
    }

    companion object {

        private val TAG = "SmsBroadcastReceiver"
    }
}
