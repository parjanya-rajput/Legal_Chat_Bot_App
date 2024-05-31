package com.example.legal_chat_bot.network

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Url
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface ChatServiceInterface {

    @FormUrlEncoded
    @POST("query")
    fun sendMessage(
        @Field("query") query: String,
    ): Call<APIData>

}