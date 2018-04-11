package fr.intechinfo.fusionandroid;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String strTitle=remoteMessage.getNotification().getTitle();
        String message=remoteMessage.getNotification().getBody();
        Log.d(TAG,"onMessageReceived:  Message Received: \n" + "Title: " + strTitle + "\n" + "Message: "+ message);

        sendSMS(strTitle, message);
        //sendNotification(strTitle,message);
    }
    private void sendSMS(String phoneNumber, String messageBody){
        SmsManager sms = SmsManager.getDefault();


        sms.sendTextMessage(phoneNumber, null, messageBody,null,null);
    }

}
