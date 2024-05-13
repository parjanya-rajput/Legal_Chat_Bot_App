package com.example.legal_chat_bot

import android.app.Application
import com.google.firebase.FirebaseApp

class AuthApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}