package com.example.myapplication.data.datasource.remote

import com.example.myapplication.data.dto.ChatDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface SendChatService {
    @POST("/send-chat")
    suspend fun sendChat(@Body customMessage: ChatDto.CustomMessage):Response<Void>
}