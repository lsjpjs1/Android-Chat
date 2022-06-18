package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.datasource.remote.stomp.Event
import com.example.myapplication.data.datasource.remote.stomp.StompClient
import com.example.myapplication.data.dto.ChatDto
import com.example.myapplication.data.repository.ChatRoomRepository
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainViewModel : ViewModel() {
    private val _chatRooms = MutableLiveData<Map<Long,ChatDto.ChatRoomDto>>()
    val chatRooms = _chatRooms as LiveData<Map<Long,ChatDto.ChatRoomDto>>

    private val stompClient = StompClient( OkHttpClient(), 1000L,BuildConfig.CHAT_SOCKET_URL)

    init {
        getChatRooms()
        stompClient.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {
                    stompClient.join("/topic/1").subscribe {
                        it?.let {
                            val customMessage: ChatDto.CustomMessage = Gson().fromJson(it, ChatDto.CustomMessage::class.java)
                            getCustomMessage(customMessage)
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

    fun getChatRooms() {
        GlobalScope.launch {
            _chatRooms.postValue(ChatRoomRepository.getChatRooms().map { it.chatRoomId to it }.toMap())
        }
    }

    fun getCustomMessage(customMessage: ChatDto.CustomMessage) {
        _chatRooms.value?.let {
            val mutableMap:MutableMap<Long,ChatDto.ChatRoomDto> = HashMap(it)

            it[customMessage.channelId]?.let {
                val chatRoomDto = ChatDto.ChatRoomDto(it)
                chatRoomDto.recentMessage = customMessage.body
                chatRoomDto.notReadMessageCount +=1
                chatRoomDto.recentMessageTime = customMessage.sendTime
                mutableMap[customMessage.channelId] = chatRoomDto
            }

            _chatRooms.postValue(mutableMap)
        }
    }
}