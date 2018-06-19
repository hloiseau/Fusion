package fr.intechinfo.fusionandroid

import org.webrtc.IceCandidate
import org.webrtc.SessionDescription
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url
import okhttp3.ResponseBody



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

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/rtc")
    fun SetLocalDesc(@Body desc: SessionDescription): Call<SessionDescription>

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/contact")
    fun SetNewCandidate(@Body iceCandidate: IceCandidate): Call<IceCandidate>

    @GET("/api/file/getfile")
    fun downloadFileWithDynamicUrlSync(): Call<ResponseBody>
}
