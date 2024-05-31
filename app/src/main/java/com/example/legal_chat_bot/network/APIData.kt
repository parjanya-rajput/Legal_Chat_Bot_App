package com.example.legal_chat_bot.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class APIData(
    @SerializedName("response") var generatedText: String? = null
) : Serializable
