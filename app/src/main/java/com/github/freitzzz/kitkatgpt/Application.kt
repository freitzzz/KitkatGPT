package com.github.freitzzz.kitkatgpt

import android.app.Application
import com.github.freitzzz.kitkatgpt.core.vault
import com.github.freitzzz.kitkatgpt.data.ConversationRepository
import com.github.freitzzz.kitkatgpt.data.FakeConversationRepository
import com.github.freitzzz.kitkatgpt.domain.StartConversation
import com.github.freitzzz.kitkatgpt.domain.UpdateConversation

class Application : Application() {

    init {
        vault().apply {
            store<ConversationRepository>(FakeConversationRepository())
            store(StartConversation(get()))
            store(UpdateConversation(get()))
        }
    }
}