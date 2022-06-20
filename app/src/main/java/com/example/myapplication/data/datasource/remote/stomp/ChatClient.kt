package com.example.myapplication.data.datasource.remote.stomp

import com.example.myapplication.BuildConfig
import okhttp3.OkHttpClient

class ChatClient {

    companion object{
        fun newInstance(): StompClient {
            return StompClient( OkHttpClient(), 1000L, BuildConfig.CHAT_SOCKET_URL)
        }
    }

}