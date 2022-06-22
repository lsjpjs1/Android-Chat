package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.datasource.remote.ChatClient
import com.example.myapplication.data.dto.ChatDto
import com.example.myapplication.data.repository.ChatMessageRepository
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Timestamp

class InChatViewModel : ViewModel() {
    private val _chatMessages = MutableLiveData<MutableList<ChatDto.ChatMessageDto>>()
    private val currentChatRoomId = MutableLiveData<Long>()
    val chatMessages = _chatMessages as LiveData<MutableList<ChatDto.ChatMessageDto>>

    val sendChatMessageBody = MutableLiveData<String>()

    private val chatClient = ChatClient.newInstance()

    fun getChatMessages(chatRoomId: Long) {
        GlobalScope.launch {
            _chatMessages.postValue(
                ChatMessageRepository.getChatMessages(
                    ChatDto.GetChatMessageRequest(
                        chatRoomId
                    )
                ).toMutableList()
            )
        }
    }

    fun connectChatRoomSocket(chatRoomId: Long) {
        chatClient.topic("/topic/$chatRoomId").subscribe{
            it?.let {
                val customMessage: ChatDto.ChatMessageDto =
                    Gson().fromJson(it.payload, ChatDto.ChatMessageDto::class.java)
                getCustomMessage(customMessage)
            }
        }
        chatClient.connect()

    }

    fun getCustomMessage(customMessage: ChatDto.ChatMessageDto) {

        _chatMessages.value?.let {
            val copyList = it.toMutableList()
            copyList.add(0,customMessage)
            _chatMessages.postValue(copyList)
        }
    }

    fun setCurrentChatRoomId(chatRoomId: Long) {
        currentChatRoomId.postValue(chatRoomId)
    }

    fun sendMessage() {

        currentChatRoomId.value?.let { currentChatRoomId ->
            GlobalScope.launch {

                ChatMessageRepository.sendChat(
                    ChatDto.CustomMessage(
                        "me",
                        sendChatMessageBody.value ?: "",
                        currentChatRoomId,
                        Timestamp(System.currentTimeMillis())
                    )
                )

            }

        }


    }

}
