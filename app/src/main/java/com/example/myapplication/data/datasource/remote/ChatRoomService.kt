package com.example.myapplication.data.datasource.remote

import com.example.myapplication.data.dto.ChatDto
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ChatRoomService {
    @GET("/chat-rooms")
    suspend fun getChatRooms(): Response<ChatDto.GetChatRoomResponse>
}