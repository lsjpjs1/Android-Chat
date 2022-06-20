package com.example.myapplication.data.dto

import java.sql.Timestamp

class ChatDto {
    data class ChatRoomDto(
        val chatRoomId: Long,
        var recentMessage: String,
        var recentMessageTime: Timestamp,
        var notReadMessageCount: Int
    ) {
        constructor(chatRoomDto: ChatRoomDto) : this(
            chatRoomDto.chatRoomId,
            chatRoomDto.recentMessage,
            chatRoomDto.recentMessageTime,
            chatRoomDto.notReadMessageCount
        )
    }

    data class GetChatRoomResponse(
        val chatRooms: List<ChatRoomDto>
    )

    data class CustomMessage(
        val sender: String,
        val body: String,
        val channelId: Long,
        val sendTime: Timestamp
    )

    data class ChatMessageDto(
        val chatMessageId: Long,
        val chatMessageBody: String,
        val chatMessageTime: Timestamp,
        val sendUserInfoId: Long,
        val sendUserNickname: String,
        val isMine: Boolean,
        val chatRoomId: Long
    )

    data class GetChatMessageResponse(
        val chatMessages: List<ChatMessageDto>
    )

    data class GetChatMessageRequest(
        val chatRoomId: Long
    )
}