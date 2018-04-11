package fr.intechinfo.fusionandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("Contacts/new")
    Call<List<Contact>> CreateContacts(@Body List<Contact> lsContact);
}
