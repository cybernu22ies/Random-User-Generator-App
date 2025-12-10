package com.example.randomuser.data.network

import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RandomUserApi(private val client: OkHttpClient) {
    private val baseUrl = "https://randomuser.me/api/".toHttpUrl()

    suspend fun requestRandomUser(gender: String, nat: String): UserDto {
        val singleUserRequestUrl = baseUrl.newBuilder()
            .addQueryParameter("gender", gender)
            .addQueryParameter("nat", nat)
            .addQueryParameter("results", "1")
            .addQueryParameter("inc", "gender,name,location,email,dob,phone,picture,nat")
            .addQueryParameter("noinfo", null)
            .build()

        val request = Request.Builder()
            .url(singleUserRequestUrl)
            .build()

        return suspendCancellableCoroutine { continuation ->
            val call = client.newCall(request)

            continuation.invokeOnCancellation {
                call.cancel()
            }

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        try {
                            val userDto = response.body.string().let {
                                Json.decodeFromString<UsersResponse>(it).results[0]
                            }
                            continuation.resume(userDto)
                        } catch (e: Exception) {
                            continuation.resumeWithException(e)
                        }
                    } else {
                        continuation.resumeWithException(IOException("Unexpected code ${response.code}"))
                    }
                }
            })
        }
    }
}