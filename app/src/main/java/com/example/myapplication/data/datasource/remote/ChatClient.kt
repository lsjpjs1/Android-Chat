package com.example.myapplication.data.datasource.remote

import com.example.myapplication.BuildConfig
import okhttp3.OkHttpClient
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

class ChatClient {

    companion object{
        fun newInstance(): StompClient {
            return Stomp.over(Stomp.ConnectionProvider.OKHTTP, BuildConfig.CHAT_SOCKET_URL)
        }
    }

}