package fr.intechinfo.fusionandroid

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitAPI {


    @Headers("'Content-Type': 'application/json'")
    @POST("/api/contact/sync")
    fun CreateContacts(@Body lsContact: ContactsList): Call<ContactsList>

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/sms/receivesms")
    fun CreateSMS(@Body lsSMS: SMSList): Call<SMSList>

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/contact")
    fun CreateNewDevice(@Body token: Token): Call<Token>
}
