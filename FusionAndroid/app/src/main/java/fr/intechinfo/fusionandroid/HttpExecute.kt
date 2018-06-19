package fr.intechinfo.fusionandroid

import android.util.Log


import java.io.IOException

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpExecute(internal var _call: Call<*>) : Thread() {

    override fun run() {
        try {
            Log.d("post", _call.execute().message())
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    companion object {

        fun BuildAPI(): RetrofitAPI {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://localhost:5000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(RetrofitAPI::class.java!!)
        }
    }
}

