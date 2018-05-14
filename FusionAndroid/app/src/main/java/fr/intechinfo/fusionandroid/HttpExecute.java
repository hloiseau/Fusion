package fr.intechinfo.fusionandroid;

import android.util.Log;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpExecute extends Thread {
    
    Call _call;
    
    public HttpExecute(Call call){
        _call = call;
    }
    
   public static RetrofitAPI BuildAPI(){
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("http://192.168.42.244:5000")
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       return retrofit.create(RetrofitAPI.class);

   }

    public void run(){
        try {
            Log.d("post", _call.execute().message());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

