package com.example.legal_chat_bot.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.legal_chat_bot.R
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.livedata.observeAsState
import com.example.legal_chat_bot.data.ChatViewModel
import com.example.legal_chat_bot.network.APIData

@Composable
fun NormalTextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
            fontWeight = FontWeight.Normal

        ),
        color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
            fontWeight = FontWeight.Bold

        ),
        color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Center
    )
}

@Composable
fun MyTextFieldInfo(labelValue:String,imageVectorRes: ImageVector, onTextSelected: (String) -> Unit, onErrorStatus:Boolean){

    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = labelValue)},
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.colorPrimary),
            focusedLabelColor = colorResource(id = R.color.colorPrimary),
            cursorColor = colorResource(id = R.color.colorPrimary),
            focusedContainerColor = colorResource(id = R.color.white),
            unfocusedContainerColor = colorResource(id = R.color.colorGray),
        ),
        value = textValue.value,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(imageVector = imageVectorRes , contentDescription = "")
        },
        isError = !onErrorStatus,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        })
}


@Composable
fun PasswordTextFieldComponent(labelValue: String, onTextSelected: (String) -> Unit,onErrorStatus:Boolean){
    val password = remember {
        mutableStateOf("")
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = labelValue)},
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.colorPrimary),
            focusedLabelColor = colorResource(id = R.color.colorPrimary),
            cursorColor = colorResource(id = R.color.colorPrimary),
            focusedContainerColor = colorResource(id = R.color.white),
            unfocusedContainerColor = colorResource(id = R.color.colorGray),
        ),
        singleLine = true,
        maxLines = 1,
        value = password.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        leadingIcon = {

            Icon(Icons.Outlined.Lock, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if(passwordVisible.value){
                Icons.Outlined.Visibility
            } else {
                Icons.Outlined.VisibilityOff
            }
            val description = if(passwordVisible.value){
                stringResource(R.string.hide_password)
            } else{
                stringResource(R.string.show_password)
            }

            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !onErrorStatus,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        })
}

@Composable
fun CheckboxComponent(value: String, onTextSelected : (String)->Unit, onCheckChange : (Boolean)->Unit = {}){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        val checkedState = remember {
            mutableStateOf(false)
        }

        Checkbox(checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
                onCheckChange.invoke(it)
            } )
        ClickableTextComponent(value = value,onTextSelected)
    }
}

@Composable
fun ClickableTextComponent(value: String, onTextSelected : (String)->Unit){
    val initialText = "By continuing you accept our "
    val privacyPolicy = "Privacy Policy "
    val andText = "and "
    val termsAndConditionsText = "Terms of Use"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Color.Blue)){
            pushStringAnnotation(tag = privacyPolicy, annotation = privacyPolicy)
            append(privacyPolicy)
        }
        append(andText)
        withStyle(style = SpanStyle(color = Color.Blue)){
            pushStringAnnotation(tag = termsAndConditionsText, annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }
    }
    ClickableText(text = annotatedString , onClick = {
            offset ->
        annotatedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also{span->
                Log.d("ClickableTextComponent", "$span")

                if ((span.item == termsAndConditionsText) || (span.item == privacyPolicy)){
                    onTextSelected(span.item)
                }
            }
    } )
}

@Composable
fun ButtonComponent(value: String, onButtonClicked : () -> Unit, isEnabled : Boolean = false){
    Button(onClick = { onButtonClicked.invoke() },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnabled
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        colorResource(id = R.color.colorSecondary),
                        colorResource(id = R.color.colorPrimary)
                    )
                ),
                shape = RoundedCornerShape(50.dp)
            ),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DividerTextComponent(){
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){

        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color =  colorResource(id = R.color.black), thickness = 1.dp
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = "or",
            fontSize = 18.sp,
            color = Color.Black
        )

        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color =  colorResource(id = R.color.black), thickness = 1.dp
        )
    }
}

@Composable
fun ClickableLoginTextComponent(tryingToLogin: Boolean = true, onTextSelected : (String)->Unit){
    val initialText = if(tryingToLogin)"Already have an account? " else "Don't have an account yet? "
    val loginText = if(tryingToLogin) "Login" else "Register"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Color.Blue)){
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }
    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(40.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString , onClick = {
                offset ->
            annotatedString.getStringAnnotations(offset,offset)
                .firstOrNull()?.also{span->
                    Log.d("ClickableTextComponent", "$span")

                    if ((span.item == loginText)){
                        onTextSelected(span.item)
                    }
                }
        } )
}

@Composable
fun UnderlineTextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(40.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
            fontWeight = FontWeight.Normal

        ),
        color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolBar(toolBarTitle:String, logOutButtonClicked:()->Unit){
    TopAppBar(
        title = {
            Text(text = toolBarTitle)
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.colorSecondary),
                ),
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = stringResource(R.string.menu),
            )
        },
        actions = {
            IconButton(onClick = {
                logOutButtonClicked.invoke()
            }) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout",
                )
            }
        }
    )
}

@Composable
fun APICallButtonComponent(value: String, onButtonClicked : () -> Unit, isEnabled : Boolean = false){
    Button(onClick = { onButtonClicked.invoke() },
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorSecondary)),
        enabled = isEnabled
    ) {

        Text(text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    //response from api
    var messages by remember { mutableStateOf(listOf<String>()) }
    val messageInput by remember { mutableStateOf("") }
    //inputText is userInput
    var inputText by remember { mutableStateOf("") }
    val apiResponse by viewModel.apiResponse.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(messages.reversed()) { message ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if(isLoading == true){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .align(Alignment.CenterStart)
                        )
                    }
                    Text(
                        text = message,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
                            fontWeight = FontWeight.Normal

                        ),
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp)
                    )
                }
            }
        }
        if (isLoading == true) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.BottomStart
            ) {

                CircularProgressIndicator(
                    color = colorResource(id = R.color.black),
                )
            }
        } else {
            apiResponse?.let { response ->
                messages = listOf(response.generatedText.toString())
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.colorSecondary),
                    focusedLabelColor = colorResource(id = R.color.colorSecondary),
                    cursorColor = colorResource(id = R.color.colorSecondary),
                    focusedContainerColor = colorResource(id = R.color.white),
                    unfocusedContainerColor = colorResource(id = R.color.colorGray),
                ),
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type your message...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            APICallButtonComponent(value = "Send", onButtonClicked = {
                if (inputText.isNotBlank()) {
                    viewModel.sendMessage(inputText, APIData(messageInput))
                    inputText = ""
                }
            }, isEnabled = inputText.isNotBlank())
        }
    }
}


