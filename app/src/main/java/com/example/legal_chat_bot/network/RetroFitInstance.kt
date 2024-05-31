package com.example.legal_chat_bot.network

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


object RetroFitInstance {

    private const val BASE_URL = "https://legal-chatbot-server.onrender.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()


    private val gson = GsonBuilder()
        .registerTypeAdapter(object : TypeToken<List<APIData>>() {}.type, ResponseDeserializer())
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
            .build()
    }


    val apiInterface by lazy {
        retrofit.create(ChatServiceInterface::class.java)
    }
}