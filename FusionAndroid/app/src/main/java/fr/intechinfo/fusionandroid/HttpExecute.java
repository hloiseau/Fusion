package fr.intechinfo.fusionandroid;

import android.util.Log;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class HttpExecute extends Thread {
    
    CallSafeExecutor _call;
    
    public HttpExecute(Call call){
        _call = new CallSafeExecutor(call);
    }
    
   public static RetrofitAPI BuildAPI(){
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("http://192.168.1.38:5000")
               .addConverterFactory(JacksonConverterFactory.create())
               .build();
       return retrofit.create(RetrofitAPI.class);
   }

    public void run(){
        Log.d("post", _call.execute().message());
    }
}

