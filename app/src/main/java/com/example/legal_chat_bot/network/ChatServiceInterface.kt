package com.example.legal_chat_bot.network

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Url
import retrofit2.http.Body

interface ChatServiceInterface {

//    @POST("query?q=define law in naive term")
//    fun getResponse(): Call<List<APIData>>


    @POST
    fun sendMessage(@Url url: String, @Body apiData: APIData): Call<List<APIData>>

}