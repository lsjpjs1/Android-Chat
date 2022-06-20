package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.datasource.remote.stomp.ChatClient
import com.example.myapplication.data.datasource.remote.stomp.Event
import com.example.myapplication.data.dto.ChatDto
import com.example.myapplication.data.repository.ChatRoomRepository
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _chatRooms = MutableLiveData<Map<Long, ChatDto.ChatRoomDto>>()
    val chatRooms = _chatRooms as LiveData<Map<Long, ChatDto.ChatRoomDto>>

    private val chatClient = ChatClient.newInstance()

    init {
        getChatRooms()

    }

    fun getChatRooms() {
        GlobalScope.launch {
            _chatRooms.postValue(ChatRoomRepository.getChatRooms().map { it.chatRoomId to it }
                .toMap())
            chatClient.connect().subscribe {
                when (it.type) {
                    Event.Type.OPENED -> {
                        _chatRooms.value?.forEach { chatRoomId, chatRoom ->
                            chatClient.join("/topic/"+chatRoomId).subscribe {
                                it?.let {
                                    val chatMessageDto: ChatDto.ChatMessageDto =
                                        Gson().fromJson(it, ChatDto.ChatMessageDto::class.java)
                                    getCustomMessage(chatMessageDto)
                                }
                            }
                        }

                    }
                    Event.Type.CLOSED -> {

                    }
                    Event.Type.ERROR -> {

                    }
                }
            }
        }
    }

    fun getCustomMessage(chatMessageDto: ChatDto.ChatMessageDto) {
        _chatRooms.value?.let {
            val mutableMap: MutableMap<Long, ChatDto.ChatRoomDto> = HashMap(it)

            it[chatMessageDto.chatRoomId]?.let {
                val chatRoomDto = ChatDto.ChatRoomDto(it)
                chatRoomDto.recentMessage = chatMessageDto.chatMessageBody
                chatRoomDto.notReadMessageCount += 1
                chatRoomDto.recentMessageTime = chatMessageDto.chatMessageTime
                mutableMap[chatMessageDto.chatRoomId] = chatRoomDto

            }

            _chatRooms.postValue(mutableMap)
        }
    }

    fun clearNotReadCount(chatRoomId: Long) {
        _chatRooms.value?.let {
            val mutableMap: MutableMap<Long, ChatDto.ChatRoomDto> = HashMap(it)
            it[chatRoomId]?.let {
                val chatRoomDto = ChatDto.ChatRoomDto(it)
                chatRoomDto.notReadMessageCount = 0
                mutableMap[chatRoomId] = chatRoomDto
                _chatRooms.postValue(mutableMap)
            }
        }
    }
}