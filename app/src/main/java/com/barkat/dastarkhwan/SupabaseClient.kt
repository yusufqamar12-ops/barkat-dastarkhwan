package com.barkat.dastarkhwan

import java.net.HttpURLConnection
import java.net.URL

object SupabaseClient {
    private const val BASE_URL = "https://tsqclawevcnyftdlgtnm.supabase.co"
    private const val PUBLISHABLE_KEY = "sb_publishable_c8ahF7JZC6yEpf0C2n9hKA_gTv16pxi"

    fun submitQuote(name: String, phone: String, occasion: String, details: String, onResult: (Result<String>) -> Unit) {
        Thread {
            try {
                val url = URL("$" + "BASE_URL/rest/v1/quote_requests")
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    doOutput = true
                    setRequestProperty("apikey", PUBLISHABLE_KEY)
                    setRequestProperty("Authorization", "Bearer " + PUBLISHABLE_KEY)
                    setRequestProperty("Content-Type", "application/json")
                    setRequestProperty("Prefer", "return=representation")
                    connectTimeout = 15000
                    readTimeout = 15000
                }
                val json = "{\"name\":\"" + escape(name) + "\",\"phone\":\"" + escape(phone) + "\",\"occasion\":\"" + escape(occasion) + "\",\"details\":\"" + escape(details) + "\"}"
                connection.outputStream.use { it.write(json.toByteArray(Charsets.UTF_8)) }
                val code = connection.responseCode
                val stream = if (code in 200..299) connection.inputStream else connection.errorStream
                val response = stream?.bufferedReader()?.use { it.readText() }.orEmpty()
                if (code in 200..299) onResult(Result.success(response))
                else onResult(Result.failure(IllegalStateException("Supabase error $code: $response")))
                connection.disconnect()
            } catch (e: Exception) { onResult(Result.failure(e)) }
        }.start()
    }

    private fun escape(value: String): String = value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r")
}