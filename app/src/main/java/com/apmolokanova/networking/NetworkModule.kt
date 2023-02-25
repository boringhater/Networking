package com.apmolokanova.networking

import android.graphics.drawable.Drawable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import okhttp3.*
import java.io.IOException
import java.io.InputStream
import java.net.URL


class NetworkModule {
    companion object {
        val baseurl: String = "https://rickandmortyapi.com/api/"
        val characterSubUrl: String = "character/"
        var client = OkHttpClient()
        val json: Json = Json { ignoreUnknownKeys = true; }
        val context = Dispatchers.IO
    }

    suspend fun getCharacter(id: Int): Character? = withContext(context) {
        var char: Character? = null
        val request = Request.Builder().get().url(baseurl + characterSubUrl + id).build()
        val call = client.newCall(request)
        try {
            val response = call.execute()
            val content = response.body?.string() ?: "invalid data"
            char = json.decodeFromString<Character>(content)

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return@withContext char
    }

    suspend fun loadImageFromWebOperations(url: String?): Drawable? = withContext(context) {
        try {
            val inputStream: InputStream = URL(url).getContent() as InputStream
            return@withContext Drawable.createFromStream(inputStream, "src name")
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    suspend fun getCharactersList(): ArrayList<Character>? = withContext(context) {
        var list: ArrayList<Character>? = null
        val request = Request.Builder().get().url(baseurl + characterSubUrl).build()
        val call = client.newCall(request)
        try {
            val response = call.execute()
            val content = response.body?.string() ?: "invalid data"
            list =
                Gson().fromJson<ArrayList<Character>>(
                    json.parseToJsonElement(content).jsonObject.getValue("results").toString(),
                    object : TypeToken<ArrayList<Character>>() {}.type
                )
            response.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return@withContext list
    }
}