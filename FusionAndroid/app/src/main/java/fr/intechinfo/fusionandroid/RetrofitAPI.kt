package fr.intechinfo.fusionandroid

import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.gson.JsonElement
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.webrtc.IceCandidate
import org.webrtc.SessionDescription
import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.*


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
    @POST("/api/rtc/descriptionandroid")
    fun SetLocalDesc(@Body rtc: RtcInfo): Call<RtcInfo>

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/rtc/candidateandroid")
    fun SetNewCandidate(@Body rtc: RtcInfo): Call<RtcInfo>

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/sms/newcall")
    fun NewCall(@Body number: String): Call<String>

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/file/receivedurl")
    fun ReceivedUrl(@Body URL: String): Call<String>

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/device/createdevice")
    fun CreateDevice(@Body name: String?): Call<String>

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/device/receivestoragedata")
    fun SendStorageData(@Body storage: MainActivity.Storage): Call<MainActivity.Storage>

    @Headers("'Content-Type': 'application/json'")
    @POST("/api/device/receivebatterydata")
    fun SendBatteryData(@Body storage: MainActivity.Battery): Call<MainActivity.Battery>

    @GET("/api/file/getfile")
    fun downloadFileWithDynamicUrlSync(): Call<ResponseBody>

    @GET("/api/file/{name}")
    fun downloadFileWithDynamicUrlSync(@Path("name") documentUrl: String): Call<ResponseBody>

    @Multipart
    @POST("/api/file/fromAndroid")
    fun uploadFile(@Part filePart: MultipartBody.Part): Call<JsonElement>
}
