package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.datasource.remote.ChatClient
import com.example.myapplication.data.dto.ChatDto
import com.example.myapplication.data.repository.ChatRoomRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _chatRooms = MutableLiveData<Map<Long, ChatDto.ChatRoomDto>>()
    val chatRooms = _chatRooms as LiveData<Map<Long, ChatDto.ChatRoomDto>>



    init {
        getChatRooms()

    }

    fun getChatRooms() {
        viewModelScope.launch {
            _chatRooms.value=ChatRoomRepository.getChatRooms().map { it.chatRoomId to it }.toMap()


                _chatRooms.value?.forEach { chatRoomId, chatRoom ->
                    Log.d("chatroomid",chatRoomId.toString())
                    val chatClient = ChatClient.newInstance()
                    chatClient.topic("/topic/"+chatRoomId).subscribe {
                        it?.let {
                            val chatMessageDto: ChatDto.ChatMessageDto =
                                Gson().fromJson(it.payload, ChatDto.ChatMessageDto::class.java)
                            getCustomMessage(chatMessageDto)
                        }
                    }
                    chatClient.connect()
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