package com.example.legal_chat_bot

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.example.legal_chat_bot.app.LegalChatBotApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = getColor(R.color.colorSecondary)
            window.navigationBarColor = getColor(R.color.colorSecondary)
            LegalChatBotApp()
        }
    }
}
