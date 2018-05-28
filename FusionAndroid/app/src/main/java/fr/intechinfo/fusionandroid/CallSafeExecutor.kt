package fr.intechinfo.fusionandroid

import java.io.IOException

import retrofit2.Call
import retrofit2.Response

class CallSafeExecutor(private val call: Call<*>) {
    @Throws(Exception::class)
    fun execute(): Response<*> {
        try {
            return this.call.execute()
        } catch (e: IOException) {
            throw Exception(e)
        }

    }
}
