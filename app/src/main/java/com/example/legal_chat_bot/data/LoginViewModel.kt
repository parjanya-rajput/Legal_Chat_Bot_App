package com.example.legal_chat_bot.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.legal_chat_bot.data.ValidationResult
import com.example.legal_chat_bot.data.Validator
import com.example.legal_chat_bot.navigation.LegalChatBotAppRouter
import com.example.legal_chat_bot.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    var registrationUIState = mutableStateOf(RegistrationUIState())

    var allValidationsPassed = mutableStateOf(false)

    var allLoginValidationsPassed = mutableStateOf(false)

    var signUpInProgress = mutableStateOf(false)

    var emailVerifyPending = mutableStateOf(false)

    var registerErrorHappen = mutableStateOf(false)

    var credentialsIncorrect = mutableStateOf(false)

    val fbInstance = FirebaseAuth.getInstance()
    var userAlreadyLoggedIn = (fbInstance.currentUser != null).let {
        if(it && fbInstance.currentUser!!.isEmailVerified){
            mutableStateOf(it)
        } else {
            mutableStateOf(false)
        }
    }

    var userDetailsAddedSuccess = mutableStateOf(false)

    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
            }

            is UIEvent.lastNameChanges -> {
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.lastName
                )
            }

            is UIEvent.emailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
            }

            is UIEvent.passwordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }

            is UIEvent.RegisterButtonClicked -> {
                signUp()
            }

            is UIEvent.LoginButtonClicked -> {
                login()
            }

            is UIEvent.privacyPolicyChecked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    termsAndConditionsAccepted = event.status
                )
            }
        }
        validateDataWithRules()
    }

    private fun login() {
        Log.d("LoginViewMode", "Inside_Login")
        signInUserInFirebase(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password
        )
    }

    private fun signUp() {
        Log.d("LoginViewMode", "Inside_SignUp")
        createUserInFirebase(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password
        )

    }

    private fun validateDataWithRules() {
        val fNameResult = Validator.validateFirstName(
            fName = registrationUIState.value.firstName
        )

        val lNameResult = Validator.validateLastName(
            lName = registrationUIState.value.lastName
        )

        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )

        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = registrationUIState.value.termsAndConditionsAccepted
        )

        registrationUIState.value = registrationUIState.value.copy(
            firstNameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            termsAndConditionsError = privacyPolicyResult.status
        )
        allValidationsPassed.value = fNameResult.status && lNameResult.status && emailResult.status && passwordResult.status && privacyPolicyResult.status
        allLoginValidationsPassed.value = emailResult.status && passwordResult.status
    }

    private fun createUserInFirebase(email:String, password:String){

        signUpInProgress.value = true

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("LoginViewModel", "createUserWithEmail:success")
                    addUserDetailsInFirestore()
                    signUpInProgress.value = false
                    emailVerifyPending.value = true
                    emailVerification()
                    LegalChatBotAppRouter.navigateTo(Screen.LoginScreen)
                } else {
                    signUpInProgress.value = false
                    registerErrorHappen.value = true
                    Log.w("LoginViewModel", "createUserWithEmail:failure", task.exception)
                }
            }
    }

    private fun signInUserInFirebase(email:String, password:String){
        signUpInProgress.value = true
        val fbInstance = FirebaseAuth.getInstance()
        fbInstance.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signUpInProgress.value = false
                    val user = fbInstance.currentUser
                    if(user!!.isEmailVerified){
                        emailVerifyPending.value = false
                        LegalChatBotAppRouter.navigateTo(Screen.HomeScreen)
                        Log.d("LoginViewModel", "signInUserInFirebase:success")
                    } else {
                        emailVerifyPending.value = true
                        Log.d("LoginViewModel", "signInUserInFirebase:failure Email not verified")
                    }

                } else {
                    credentialsIncorrect.value = true
                    Log.w("LoginViewModel", "signInUserInFirebase:failure", task.exception)
                    signUpInProgress.value = false
                }
            }
    }

    private fun emailVerification() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("LoginViewModel", "Email sent.")
                } else {
                    Log.d("LoginViewModel", "Email not sent.")
                }
            }
    }

    fun signOutUserInFirebase(){
        FirebaseAuth.getInstance().signOut()
        userAlreadyLoggedIn.value = false
        LegalChatBotAppRouter.navigateTo(Screen.LoginScreen)
    }

    fun clearViewModel() {
        registrationUIState.value = RegistrationUIState()
        allValidationsPassed.value = false
        allLoginValidationsPassed.value = false
    }

    fun addUserDetailsInFirestore(){
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val db = Firebase.firestore
            val userRef = db.collection("users").document(it.uid)
            val data = hashMapOf(
                "fName" to registrationUIState.value.firstName,
                "lName" to registrationUIState.value.lastName,
            )

            userRef.set(data)
                .addOnSuccessListener {
                    userDetailsAddedSuccess.value = true
                    Log.d("LoginViewModel", "DocumentSnapshot added with ID: ${it}")
                }
                .addOnFailureListener { e ->
                    Log.w("LoginViewModel", "Error adding document", e)
                }
        }
    }

}