package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.ChatRoomRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    init {
        getChatRooms()
    }
    fun getChatRooms() {
        GlobalScope.launch {
            val chatRooms = ChatRoomRepository.getChatRooms()
            chatRooms.stream().forEach { a -> Log.d("chatRoom",a.toString()) }
        }
    }
}