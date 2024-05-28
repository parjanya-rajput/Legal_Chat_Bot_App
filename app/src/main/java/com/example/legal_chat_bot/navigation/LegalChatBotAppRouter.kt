package com.example.legal_chat_bot.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.legal_chat_bot.data.LoginViewModel

sealed class Screen(){
    object RegisterScreen : Screen()
    object TermsAndConditionsScreen : Screen()
    object LoginScreen : Screen()
    object HomeScreen : Screen()
}
object LegalChatBotAppRouter {

    val currentScreen : MutableState<Screen> = mutableStateOf(Screen.HomeScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
}