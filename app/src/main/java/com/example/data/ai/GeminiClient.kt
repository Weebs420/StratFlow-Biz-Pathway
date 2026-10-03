package com.example.data.ai

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiClient {
    private const val TAG = "GeminiClient"
    private const val MODEL_NAME = "gemini-3.5-flash"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent"

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    fun isConfigured(): Boolean {
        val key = BuildConfig.GEMINI_API_KEY
        return key.isNotBlank() && key != "MY_GEMINI_API_KEY"
    }

    suspend fun generateContent(systemPrompt: String, userPrompt: String): String? = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            Log.d(TAG, "No valid Gemini API key present in BuildConfig.")
            return@withContext null
        }

        try {
            val root = JSONObject()

            // System instruction
            val sysInstObj = JSONObject()
            val sysPartsArr = JSONArray()
            sysPartsArr.put(JSONObject().put("text", systemPrompt))
            sysInstObj.put("parts", sysPartsArr)
            root.put("systemInstruction", sysInstObj)

            // Contents
            val contentsArr = JSONArray()
            val contentObj = JSONObject()
            val partsArr = JSONArray()
            partsArr.put(JSONObject().put("text", userPrompt))
            contentObj.put("parts", partsArr)
            contentsArr.put(contentObj)
            root.put("contents", contentsArr)

            // Generation config with JSON response
            val genConfig = JSONObject()
            genConfig.put("responseMimeType", "application/json")
            genConfig.put("temperature", 0.4)
            root.put("generationConfig", genConfig)

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body = root.toString().toRequestBody(mediaType)

            val request = Request.Builder()
                .url("$BASE_URL?key=$apiKey")
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.w(TAG, "Gemini call failed with code: ${response.code}")
                return@withContext null
            }

            val respBodyStr = response.body?.string() ?: return@withContext null
            val respJson = JSONObject(respBodyStr)
            val candidates = respJson.optJSONArray("candidates") ?: return@withContext null
            if (candidates.length() == 0) return@withContext null

            val firstCandidate = candidates.getJSONObject(0)
            val content = firstCandidate.optJSONObject("content") ?: return@withContext null
            val parts = content.optJSONArray("parts") ?: return@withContext null
            if (parts.length() == 0) return@withContext null

            val text = parts.getJSONObject(0).optString("text", "")
            if (text.isNotBlank()) text else null
        } catch (e: Exception) {
            Log.e(TAG, "Gemini API exception", e)
            null
        }
    }
}
