package com.example.legal_chat_bot.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.legal_chat_bot.R
import com.example.legal_chat_bot.components.AppToolBar
import com.example.legal_chat_bot.components.ButtonComponent
import com.example.legal_chat_bot.components.HeadingTextComponent
import com.example.legal_chat_bot.data.LoginViewModel

@Composable
fun HomeScreen(loginViewModel: LoginViewModel = viewModel()) {

    Scaffold(
        topBar = {
            AppToolBar(toolBarTitle = stringResource(id = R.string.app_name),
                logOutButtonClicked = {
                loginViewModel.signOutUserInFirebase()
            })
        }
    ){
        paddingValues ->
            Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
    ) {
        Column(modifier = Modifier.fillMaxSize()){

        }
    }

    }
}
