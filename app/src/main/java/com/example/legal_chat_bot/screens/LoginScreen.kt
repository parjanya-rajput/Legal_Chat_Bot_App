package com.example.legal_chat_bot.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.legal_chat_bot.R
import com.example.legal_chat_bot.components.AppToolBar
import com.example.legal_chat_bot.components.ButtonComponent
import com.example.legal_chat_bot.components.ClickableLoginTextComponent
import com.example.legal_chat_bot.components.DividerTextComponent
import com.example.legal_chat_bot.components.HeadingTextComponent
import com.example.legal_chat_bot.components.MyTextFieldInfo
import com.example.legal_chat_bot.components.NormalTextComponent
import com.example.legal_chat_bot.components.PasswordTextFieldComponent
import com.example.legal_chat_bot.components.UnderlineTextComponent
import com.example.legal_chat_bot.data.LoginViewModel
import com.example.legal_chat_bot.data.UIEvent
import com.example.legal_chat_bot.navigation.LegalChatBotAppRouter
import com.example.legal_chat_bot.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()){
    loginViewModel.clearViewModel()

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {

            Surface(
                modifier = Modifier
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                Column {
                    if(loginViewModel.userAlreadyLoggedIn.value){
                        LegalChatBotAppRouter.navigateTo(Screen.HomeScreen)
                    }
                    NormalTextComponent(value = stringResource(id = R.string.greeting))
                    HeadingTextComponent(value = stringResource(id = R.string.welcome_back))
                    Spacer(modifier = Modifier.height(20.dp))
                    MyTextFieldInfo(
                        labelValue = stringResource(id = R.string.email),
                        imageVectorRes = Icons.Outlined.Email,
                        onTextSelected = {
                            loginViewModel.onEvent(UIEvent.emailChanged(it))
                        },
                        onErrorStatus = loginViewModel.registrationUIState.value.emailError
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    PasswordTextFieldComponent(
                        labelValue = stringResource(id = R.string.password),
                        onTextSelected = {
                            loginViewModel.onEvent(UIEvent.passwordChanged(it))
                        },
                        onErrorStatus = loginViewModel.registrationUIState.value.passwordError
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    UnderlineTextComponent(value = stringResource(id = R.string.forgot_your_password))
                    Spacer(modifier = Modifier.height(40.dp))
                    ButtonComponent(value = stringResource(id = R.string.login), onButtonClicked = {
                        loginViewModel.onEvent(UIEvent.LoginButtonClicked)
                    }, isEnabled = loginViewModel.allLoginValidationsPassed.value)
                    Spacer(modifier = Modifier.height(20.dp))
                    DividerTextComponent()
                    Spacer(modifier = Modifier.height(10.dp))
                    ClickableLoginTextComponent(false, onTextSelected = {
                        LegalChatBotAppRouter.navigateTo(Screen.RegisterScreen)
                    })
                    if (loginViewModel.emailVerifyPending.value) {
                        LaunchedEffect(key1 = loginViewModel.emailVerifyPending.value) {
                            try {
                                snackbarScope.launch {
                                    snackbarHostState.showSnackbar("Verify Your Email Address to Login")
                                    loginViewModel.emailVerifyPending.value = false
                                }
                            } catch (e: Exception) {
                                snackbarScope.launch {
                                    snackbarHostState.showSnackbar(e.message.toString())
                                }
                            }
                        }
                    }
                    if(loginViewModel.credentialsIncorrect.value){
                        LaunchedEffect(key1 = loginViewModel.credentialsIncorrect.value) {
                            try {
                                snackbarScope.launch {
                                    snackbarHostState.showSnackbar("Invalid Credentials")
                                }
                                loginViewModel.credentialsIncorrect.value = false
                            } catch (e: Exception) {
                                snackbarScope.launch {
                                    snackbarHostState.showSnackbar(e.message.toString())
                                }
                            }
                        }
                    }
                }
                BackHandler {
                    LegalChatBotAppRouter.navigateTo(Screen.RegisterScreen)
                }
            }
            if (loginViewModel.signUpInProgress.value) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
fun DefaultLoginScreenPreview(){
    LoginScreen()
}