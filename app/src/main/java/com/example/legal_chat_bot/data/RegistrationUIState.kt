package com.example.legal_chat_bot.data

data class RegistrationUIState (
    var firstName : String = "",
    var lastName : String = "",
    var email : String = "",
    var password: String = "",
    var termsAndConditionsAccepted: Boolean = false,
    var firstNameError: Boolean = false,
    var lastNameError: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var termsAndConditionsError: Boolean = false
)