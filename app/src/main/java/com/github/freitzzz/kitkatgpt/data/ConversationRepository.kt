package com.github.freitzzz.kitkatgpt.data

import com.github.freitzzz.kitkatgpt.data.model.Message
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

interface ConversationRepository {
    fun start(input: String): Flow<Message>
    fun prompt(input: String): Flow<Message>
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