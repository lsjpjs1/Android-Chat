package com.example.myapplication.data.dto

import java.sql.Timestamp

class ChatDto {
    data class ChatRoomDto(
        val chatRoomId:Long,
        val recentMessage:String,
        val recentMessageTime: Timestamp,
        val notReadMessageCount: Int
    )

    data class GetChatRoomResponse(
        val chatRooms:List<ChatRoomDto>
    )
}