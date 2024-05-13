package com.example.legal_chat_bot.data

object Validator {

    fun validateFirstName(fName:String) : ValidationResult{
        return ValidationResult(
            (!fName.isNullOrEmpty() && fName.length >=2)
        )
    }

    fun validateLastName(lName:String):ValidationResult{
        return ValidationResult(
            (!lName.isNullOrEmpty() && lName.length >=2)
        )
    }

    fun validateEmail(email:String): ValidationResult{
        return ValidationResult(
            (!email.isNullOrEmpty() && email.length >=10)
        )
    }

    fun validatePassword(password:String): ValidationResult {
        return ValidationResult(
            (!password.isNullOrEmpty() && password.length >=8)
        )
    }
    fun validatePrivacyPolicyAcceptance(statusValue:Boolean) : ValidationResult{
        return ValidationResult(statusValue)
    }
}

data class ValidationResult(
    val status:Boolean = false
)