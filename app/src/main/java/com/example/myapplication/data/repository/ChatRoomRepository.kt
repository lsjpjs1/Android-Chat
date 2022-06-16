package com.example.myapplication.data.repository

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.datasource.remote.ChatRoomService
import com.example.myapplication.data.datasource.remote.RetrofitClient
import com.example.myapplication.data.dto.ChatDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatRoomRepository {

    private val chatRoomService: ChatRoomService =
        RetrofitClient.getClient(BuildConfig.BASE_URL).create(ChatRoomService::class.java)

    suspend fun getChatRooms(): List<ChatDto.ChatRoomDto> {
        return withContext(Dispatchers.IO) {
            chatRoomService.getChatRooms()
        }.body()?.chatRooms ?: arrayListOf()  //예외처리 안되어있음

    }
}