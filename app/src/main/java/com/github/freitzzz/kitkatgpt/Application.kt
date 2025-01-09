package com.github.freitzzz.kitkatgpt

import android.app.Application
import com.github.freitzzz.kitkatgpt.core.vault
import com.github.freitzzz.kitkatgpt.data.ConversationRepository
import com.github.freitzzz.kitkatgpt.data.OllamaConversationRepository
import com.github.freitzzz.kitkatgpt.data.http.NetworkingClient
import com.github.freitzzz.kitkatgpt.domain.StartConversation
import com.github.freitzzz.kitkatgpt.domain.UpdateConversation

class Application : Application() {

    init {
        vault().apply {
            val client = NetworkingClient("http://192.168.1.164:11434/")

            store<ConversationRepository>(OllamaConversationRepository(client))
            store(StartConversation(get()))
            store(UpdateConversation(get()))
        }
    }
}