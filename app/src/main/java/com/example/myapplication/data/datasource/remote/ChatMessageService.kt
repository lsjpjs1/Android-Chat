package com.example.myapplication.data.datasource.remote

import com.example.myapplication.data.dto.ChatDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatMessageService {
    @GET("/chat-messages/{chatRoomId}")
    suspend fun getChatMessages(@Path("chatRoomId") chatRoomId: Long): Response<ChatDto.GetChatMessageResponse>
}