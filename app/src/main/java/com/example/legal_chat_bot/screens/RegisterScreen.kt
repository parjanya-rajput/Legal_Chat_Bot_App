package com.example.legal_chat_bot.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.legal_chat_bot.R
import com.example.legal_chat_bot.components.AppToolBar
import com.example.legal_chat_bot.components.ButtonComponent
import com.example.legal_chat_bot.navigation.Screen
import com.example.legal_chat_bot.components.CheckboxComponent
import com.example.legal_chat_bot.components.ClickableLoginTextComponent
import com.example.legal_chat_bot.components.DividerTextComponent
import com.example.legal_chat_bot.components.HeadingTextComponent
import com.example.legal_chat_bot.components.MyTextFieldInfo
import com.example.legal_chat_bot.components.NormalTextComponent
import com.example.legal_chat_bot.components.PasswordTextFieldComponent
import com.example.legal_chat_bot.data.LoginViewModel
import com.example.legal_chat_bot.data.UIEvent
import com.example.legal_chat_bot.navigation.LegalChatBotAppRouter
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(loginViewModel: LoginViewModel = viewModel()) {
    loginViewModel.clearViewModel()

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
//        topBar = {
//            TopAppBar(
//                title = {
//                    NormalTextComponent(value = )
//                }
//            )
//        }
    ) {paddingValues ->
        Box(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Surface(
            modifier = Modifier
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column {
                HeadingTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldInfo(labelValue = stringResource(id = R.string.first_name),Icons.Filled.PersonOutline, onTextSelected = {
                    loginViewModel.onEvent(UIEvent.FirstNameChanged(it))
                },onErrorStatus = loginViewModel.registrationUIState.value.firstNameError)
                Spacer(modifier = Modifier.height(10.dp))
                MyTextFieldInfo(labelValue = stringResource(id = R.string.last_name),Icons.Filled.PersonOutline, onTextSelected = {
                    loginViewModel.onEvent(UIEvent.lastNameChanges(it))
                },onErrorStatus = loginViewModel.registrationUIState.value.lastNameError)
                Spacer(modifier = Modifier.height(10.dp))
                MyTextFieldInfo(labelValue = stringResource(id = R.string.email), Icons.Outlined.Email, onTextSelected = {
                    loginViewModel.onEvent(UIEvent.emailChanged(it))
                },onErrorStatus = loginViewModel.registrationUIState.value.emailError)
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password), onTextSelected = {
                    loginViewModel.onEvent(UIEvent.passwordChanged(it))
                },onErrorStatus = loginViewModel.registrationUIState.value.passwordError)
                CheckboxComponent(value = stringResource(id = R.string.terms_and_conditions),
                    onTextSelected = {
                        LegalChatBotAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
                    }, onCheckChange = {
                        loginViewModel.onEvent(UIEvent.privacyPolicyChecked(it))
                    })
                Spacer(modifier = Modifier.height(30.dp))
                ButtonComponent(value = stringResource(R.string.register), onButtonClicked = {
                    loginViewModel.onEvent(UIEvent.RegisterButtonClicked)
                }, isEnabled = loginViewModel.allValidationsPassed.value)
                DividerTextComponent()
                Spacer(modifier = Modifier.height(60.dp))
                ClickableLoginTextComponent(true,onTextSelected = {
                    LegalChatBotAppRouter.navigateTo(Screen.LoginScreen)
                })
                if (loginViewModel.registerErrorHappen.value) {
                        LaunchedEffect(key1 = loginViewModel.registerErrorHappen.value) {
                            try {
                                snackbarScope.launch {
                                    snackbarHostState.showSnackbar("Something Went Wrong! Please Try Again")
                                    loginViewModel.registerErrorHappen.value = false
                                }
                            } catch (e: Exception) {
                                snackbarScope.launch {
                                    snackbarHostState.showSnackbar(e.message.toString())
                                }
                            }
                        }
                    }
            }
        }

        if(loginViewModel.signUpInProgress.value){
            CircularProgressIndicator()
        }
    }
    }

}

@Preview
@Composable
fun DefaultPreviewOfRegisterScreen(){
    RegisterScreen()
}