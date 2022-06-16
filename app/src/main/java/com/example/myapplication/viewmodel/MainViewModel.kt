package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.ChatDto
import com.example.myapplication.data.repository.ChatRoomRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _chatRooms = MutableLiveData<List<ChatDto.ChatRoomDto>>()
    val chatRooms = _chatRooms as LiveData<List<ChatDto.ChatRoomDto>>

    init {
        getChatRooms()
    }
    fun getChatRooms() {
        GlobalScope.launch {
            _chatRooms.postValue(ChatRoomRepository.getChatRooms())

        }
    }
}