package fr.intechinfo.fusionandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;


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
        final Call<String> stringCall = retrofitAPI.CreateNewDevice(token);
        HttpExecute t = new HttpExecute(stringCall);
        t.start();
    }
}
