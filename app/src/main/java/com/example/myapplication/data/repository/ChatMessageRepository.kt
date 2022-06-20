package com.example.myapplication.data.repository

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.datasource.remote.ChatMessageService
import com.example.myapplication.data.datasource.remote.RetrofitClient
import com.example.myapplication.data.datasource.remote.SendChatService
import com.example.myapplication.data.dto.ChatDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatMessageRepository {
    private val chatMessageService: ChatMessageService = RetrofitClient.getClient(BuildConfig.BASE_URL).create(ChatMessageService::class.java)
    private val sendChatService: SendChatService = RetrofitClient.getClient(BuildConfig.BASE_URL).create(SendChatService::class.java)


    suspend fun getChatMessages(getChatMessageRequest: ChatDto.GetChatMessageRequest): List<ChatDto.ChatMessageDto> {
        return withContext(Dispatchers.IO) {
            chatMessageService.getChatMessages(getChatMessageRequest.chatRoomId)
        }.body()?.chatMessages?: arrayListOf()
    }

    suspend fun sendChat(customMessage: ChatDto.CustomMessage){
        withContext(Dispatchers.IO) {
            sendChatService.sendChat(customMessage)
        }
    }
}