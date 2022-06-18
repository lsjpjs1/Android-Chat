package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.ChatDto
import com.example.myapplication.data.repository.ChatMessageRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InChatViewModel : ViewModel() {
    private val _chatMessages = MutableLiveData<MutableList<ChatDto.ChatMessageDto>>()
    val chatMessages = _chatMessages as LiveData<MutableList<ChatDto.ChatMessageDto>>

    fun getChatMessages(chatRoomId: Long) {
        GlobalScope.launch {
            _chatMessages.postValue(ChatMessageRepository.getChatMessages(ChatDto.GetChatMessageRequest(chatRoomId)).toMutableList())
        }
    }
}