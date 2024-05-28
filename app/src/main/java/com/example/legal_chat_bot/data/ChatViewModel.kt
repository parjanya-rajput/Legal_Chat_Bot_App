package com.example.legal_chat_bot.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legal_chat_bot.network.APIData
import com.example.legal_chat_bot.network.RetroFitInstance
import kotlinx.coroutines.Dispatchers
import retrofit2.Callback
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ChatViewModel: ViewModel() {

//    private val _apiResponse = MutableLiveData<APIData>()
//    val apiResponse: LiveData<APIData> get() = _apiResponse

    private val _apiResponse = MutableLiveData<List<APIData>>()
    val apiResponse: LiveData<List<APIData>> get() = _apiResponse

//    fun sendMessage(userInput: String, apiData: APIData) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val url = "query?q=$userInput"
//            RetroFitInstance.apiInterface.sendMessage(url, apiData).enqueue(object : Callback<APIData> {
//
//                    override fun onResponse(
//                        call: Call<APIData>,
//                        response: Response<APIData>
//                    ) {
//                        if (response.isSuccessful) {
//                            _apiResponse.postValue(response.body())
////                            println(response.body())
//                        }else {
//                            Log.e("API Error", "Error: ${response.errorBody()?.string()}")
//                        }
//                    }
//
//                override fun onFailure(call: Call<APIData>, t: Throwable) {
//                    Log.e("API Failure", "Failure: ${t.message}")
//                }
//            })
//        }
//    }


    fun sendMessage(userInput: String, apiData: APIData) {
        viewModelScope.launch(Dispatchers.IO) {
            val url = "query?q=$userInput"
            RetroFitInstance.apiInterface.sendMessage(url, apiData).enqueue(object : Callback<List<APIData>> {

                override fun onResponse(
                    call: Call<List<APIData>>,
                    response: Response<List<APIData>>
                ) {
                    if (response.isSuccessful) {
                        _apiResponse.postValue(response.body())
                        println(response.body())
                    }else {
                        Log.e("API Error", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<List<APIData>>, t: Throwable) {
                    Log.e("API Failure", "Failure: ${t.message}")
                }
            })
        }
    }

//    fun sendReqToModel() {
//        RetroFitInstance.apiInterface.getResponse().enqueue(object : Callback<List<APIData>>{
//            override fun onResponse(call: Call<List<APIData>>, response: Response<List<APIData>>) {
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    println(data)
//                }
//            }
//
//            override fun onFailure(call: Call<List<APIData>>, t: Throwable) {
//                // Handle failure
//                println("Failed to connect to API " + t.message)
//            }
//
//        })
//    }
}