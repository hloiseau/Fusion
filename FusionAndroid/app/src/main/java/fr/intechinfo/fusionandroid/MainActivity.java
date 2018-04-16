package fr.intechinfo.fusionandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        FirebaseMessaging.getInstance().subscribeToTopic("ServiceNow");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.8.111.192:5000")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        retrofitAPI.CreateNewDevice(FirebaseInstanceId.getInstance().getToken());

        PermissionUtil.initPermissions(this);
    }
}
