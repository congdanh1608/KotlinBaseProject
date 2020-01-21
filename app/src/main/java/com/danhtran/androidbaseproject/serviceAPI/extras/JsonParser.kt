package com.danhtran.androidbaseproject.serviceAPI.extras

import com.google.gson.*
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener

import java.lang.reflect.Type
import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * Created by danhtran on 13/04/2017.
 */

object JsonParser {
    private val serializerDate = JsonSerializer<Date> { src, typeOfSrc, context ->
        if (src == null) null else JsonPrimitive(
            TimeUnit.SECONDS.toMillis(src.time)
        )
    }
    private val deserializerDate = JsonDeserializer<Date> { json, typeOfT, context ->
        if (json == null) null else Date(
            TimeUnit.SECONDS.toMillis(json.asLong)
        )
    }

    fun toJson(`object`: Any): String {
        return Gson().toJson(`object`)
    }

    fun toJsonElement(`object`: Any): JsonElement {
        return Gson().toJsonTree(`object`)
    }

    @Throws(JSONException::class)
    fun <Object> fromJson(stringJson: String, classOfT: Class<Object>): Object? {
        val `object` = JSONTokener(stringJson).nextValue() as JSONObject
        val gson = GsonBuilder().registerTypeAdapter(Date::class.java, serializerDate)
            .registerTypeAdapter(Date::class.java, deserializerDate)
            .create()
        return if (`object` != null) gson.fromJson(stringJson, classOfT) else null
    }

    fun fromJsonElement(params: Map<String, Any>): JsonElement? {
        var jsonData: JsonElement? = null
        try {
            jsonData = JsonParser.fromJson(JsonParser.toJson(params), JsonElement::class.java)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return jsonData
    }
}