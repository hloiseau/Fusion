package fr.intechinfo.fusionandroid;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/contact/synccontact")
    Call<List<Contact>> CreateContacts(@Body List<Contact> lsContact);

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/sms/syncsms")
    Call<List<SMS>> CreateSMS(@Body List<SMS> lsSMS);

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/contact")
    Call<String> CreateNewDevice(@Body Token token);


}
