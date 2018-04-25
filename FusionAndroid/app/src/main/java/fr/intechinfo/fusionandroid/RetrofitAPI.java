package fr.intechinfo.fusionandroid;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitAPI {


    @Headers("'Content-Type': 'application/json'")
    @POST("/api/contact/sync")
    Call<ContactsList> CreateContacts(@Body ContactsList lsContact);

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/sms/receivesms")
    Call<SMSList> CreateSMS(@Body SMSList lsSMS);

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/contact")
    Call<Token> CreateNewDevice(@Body Token token);
}
