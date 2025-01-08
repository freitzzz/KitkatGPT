package com.github.freitzzz.kitkatgpt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.freitzzz.kitkatgpt.core.vault
import com.github.freitzzz.kitkatgpt.data.model.Message
import com.github.freitzzz.kitkatgpt.domain.StartConversation
import com.github.freitzzz.kitkatgpt.domain.UpdateConversation
import kotlinx.coroutines.launch

class ConversationViewModel : ViewModel() {
    private val conversation = MutableLiveData<Message>(null)

    fun start(input: String) {
        val startConversation: StartConversation by vault()
        viewModelScope.launch {
            conversation.value = Message(input, false)

            startConversation(input).collect {
                conversation.postValue(it)
            }
        }
    }

    fun update(input: String) {
        val updateConversation: UpdateConversation by vault()
        viewModelScope.launch {
            conversation.value = Message(input, false)

            updateConversation(input).collect {
                conversation.postValue(it)
            }
        }
    }

    fun messages(): LiveData<Message> = conversation
    fun isOngoing(): Boolean = conversation.value != null
}