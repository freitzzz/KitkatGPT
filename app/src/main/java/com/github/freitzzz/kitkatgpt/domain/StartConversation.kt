package com.github.freitzzz.kitkatgpt.domain

import com.github.freitzzz.kitkatgpt.data.ConversationRepository
import com.github.freitzzz.kitkatgpt.data.model.Message
import kotlinx.coroutines.flow.Flow

class StartConversation(
    private val repository: ConversationRepository,
) {
    operator fun invoke(input: String): Flow<Message> {
        return repository.start(input)
    }
}