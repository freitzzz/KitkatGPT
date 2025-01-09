package com.github.freitzzz.kitkatgpt.data

import com.github.freitzzz.kitkatgpt.data.http.Left
import com.github.freitzzz.kitkatgpt.data.http.NetworkingClient
import com.github.freitzzz.kitkatgpt.data.http.Right
import com.github.freitzzz.kitkatgpt.data.model.Message
import com.github.freitzzz.kitkatgpt.data.model.ollama.GenerateRequest
import com.github.freitzzz.kitkatgpt.data.model.ollama.GenerateResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

interface ConversationRepository {
    fun start(input: String): Flow<Message>
    fun prompt(input: String): Flow<Message>
}

class OllamaConversationRepository(
    private val client: NetworkingClient,
): ConversationRepository {
    private val messages = MutableSharedFlow<Message>()

    override fun start(input: String): Flow<Message> {
        val request = GenerateRequest(prompt = input)
        CoroutineScope(Dispatchers.IO).launch {
            val result = client.post("/api/generate", data = request.toJson())
            if(result is Left || result !is Right) {
                return@launch
            }

            val json = JSONObject(String(result.value.body))
            val response = GenerateResponse.fromJson(json)
            messages.emit(Message(reply = true, value = response.response))
        }

        return messages
    }

    override fun prompt(input: String): Flow<Message> {
        val request = GenerateRequest(prompt = input)
        CoroutineScope(Dispatchers.IO).launch {
            val result = client.post("/api/generate", data = request.toJson())
            if(result is Left || result !is Right) {
                return@launch
            }

            val json = JSONObject(String(result.value.body))
            val response = GenerateResponse.fromJson(json)
            messages.emit(Message(reply = true, value = response.response))
        }
        return messages
    }

}

class FakeConversationRepository : ConversationRepository {
    private val messages by lazy { MutableStateFlow(Message("Hi!", true)) }

    override fun start(input: String): Flow<Message> {
        return messages
    }

    override fun prompt(input: String): Flow<Message> {
        CoroutineScope(Dispatchers.Default).launch {
            delay(400)
            messages.tryEmit(Message("I'm doing fine, and you?", true))
        }

        return messages
    }
}