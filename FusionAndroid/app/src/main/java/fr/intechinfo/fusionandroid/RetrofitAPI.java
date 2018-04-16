package fr.intechinfo.fusionandroid;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @Headers("'Content-Type': 'application/json'")
    @POST("Contacts/new")
    Call<List<Contact>> CreateContacts(@Body List<Contact> lsContact);

    @Headers("'Content-Type': 'application/json'")
    @POST("Android/newToken")
    Call<String> CreateNewDevice(@Body String token);
}
