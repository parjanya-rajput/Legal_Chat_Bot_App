package com.example.legal_chat_bot.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.legal_chat_bot.navigation.LegalChatBotAppRouter
import com.example.legal_chat_bot.navigation.Screen
import com.example.legal_chat_bot.screens.HomeScreen
import com.example.legal_chat_bot.screens.LoginScreen
import com.example.legal_chat_bot.screens.RegisterScreen
import com.example.legal_chat_bot.screens.TermsAndConditionsScreen

@Composable
fun LegalChatBotApp(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color =  Color.White
    ) {
        Crossfade(targetState = LegalChatBotAppRouter.currentScreen) { currentState->
            when(currentState.value){
                is Screen.RegisterScreen->{
                    RegisterScreen()
                }
                is Screen.TermsAndConditionsScreen->{
                    TermsAndConditionsScreen()
                }
                is Screen.LoginScreen->{
                    LoginScreen()
                }
                is Screen.HomeScreen -> {
                    HomeScreen()
                }
            }

        }

    }
}