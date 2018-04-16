package fr.intechinfo.fusionandroid;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static android.content.ContentValues.TAG;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    public void sendRegistrationToServer(String Token){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.8.111.192:5000")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        retrofitAPI.CreateNewDevice(Token);
    }


}



