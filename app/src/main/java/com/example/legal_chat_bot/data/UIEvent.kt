package com.example.legal_chat_bot.data

sealed class UIEvent {
    data class FirstNameChanged(val firstName: String) : UIEvent()
    data class lastNameChanges(val lastName:String) : UIEvent()
    data class emailChanged(val email:String) : UIEvent()
    data class passwordChanged(val password:String) : UIEvent()

    data class privacyPolicyChecked(val status:Boolean) : UIEvent()

    object RegisterButtonClicked : UIEvent()

    object LoginButtonClicked : UIEvent()
}