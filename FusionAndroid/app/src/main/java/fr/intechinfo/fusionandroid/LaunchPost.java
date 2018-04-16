package fr.intechinfo.fusionandroid;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class LaunchPost extends Thread {
    public void run(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.42.33:5000")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Token token = new Token();
        token.SetToken(FirebaseInstanceId.getInstance().getToken());
        final Call<String> stringCall = retrofitAPI.CreateNewDevice(token);
        try {
            Log.d("post", stringCall.execute().message());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
