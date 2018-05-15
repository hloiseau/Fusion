package fr.intechinfo.fusionandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * A broadcast receiver who listens for incoming SMS
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "SMSBroadcastReceiver.onReceive(Context, Intent)");
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender;
            String smsBody = "";
            List<SMS> smsList = new ArrayList<>();
            long smsDate;
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsSender = smsMessage.getDisplayOriginatingAddress();
                smsBody += smsMessage.getMessageBody();
                smsDate = smsMessage.getTimestampMillis();
                smsList.add(new SMS(smsSender, smsBody, String.valueOf(smsDate), "1"));
                StringBuilder sb = new StringBuilder();
                sb.append("Sender : " +  smsSender + "\n");
                sb.append("Body : " + smsBody + "\n");
                sb.append("Date : " + smsDate + "\n");
                Log.d(TAG, sb.toString());
        }
            SMSList list = new SMSList();
            list.SetSMS(smsList);
            RetrofitAPI retrofitAPI = HttpExecute.BuildAPI();
            new HttpExecute(retrofitAPI.CreateSMS(list)).start();
        }
    }
}
