package com.github.freitzzz.kitkatgpt.data.model.ollama

import org.json.JSONObject

data class GenerateRequest(
    val prompt: String,
    val model: String = "llama3.2:1b",
    val stream: Boolean = false
) {
    fun toJson() = """
        { "prompt": "$prompt", "model": "$model", "stream": $stream }
    """.trimIndent()
}

data class GenerateResponse(
    val response: String
) {
    companion object {
        fun fromJson(json: JSONObject): GenerateResponse {
            return GenerateResponse(
                response = json.getString("response")
            )
        }
    }
}