package com.example.legal_chat_bot.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
//import kotlin.reflect.KClass

//class ResponseDeserializer<T>(private val responseType: Class<T>) : JsonDeserializer<T> {
//
//    override fun deserialize(
//        json: JsonElement,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): T {
//        val jsonObject = json.asJsonObject
//
//        return try {
//            val response = responseType.newInstance()
//
//            if (response is APIData) {
//                setFields(response, jsonObject, context)
//            }
//
//            response
//        } catch (e: InstantiationException) {
//            throw RuntimeException("Unable to create response object.", e)
//        } catch (e: IllegalAccessException) {
//            throw RuntimeException("Unable to create response object.", e)
//        }
//    }
//
//    private fun setFields(
//        response: APIData,
//        jsonObject: JsonObject,
//        context: JsonDeserializationContext?
//    ) {
//        response.generatedText = jsonObject.get("generated_text").asString
//    }
//}

class ResponseDeserializer : JsonDeserializer<List<APIData>> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<APIData> {
        return if (json.isJsonArray) {
            json.asJsonArray.map { parseAPIData(it.asJsonObject) }
        } else {
            listOf(parseAPIData(json.asJsonObject))
        }
    }

    private fun parseAPIData(jsonObject: JsonObject): APIData {
        val apiData = APIData()
        apiData.generatedText = jsonObject.get("response").asString
        return apiData
    }
}

//class ResponseDeserializer : JsonDeserializer<List<APIData>> {
//
//    override fun deserialize(
//        json: JsonElement,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): List<APIData> {
//        val jsonArray = json.asJsonArray
//        val result = mutableListOf<APIData>()
//
//        jsonArray.forEach { jsonElement ->
//            val jsonObject = jsonElement.asJsonObject
//            val apiData = APIData()
//            apiData.generatedText = jsonObject.get("generated_text").asString
//            result.add(apiData)
//        }
//
//        return result
//    }
//}
