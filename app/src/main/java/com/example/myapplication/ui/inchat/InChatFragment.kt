package com.example.myapplication.ui.inchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class InChatFragment(val chatRoomId: Long): Fragment()  {

    companion object{
        fun newInstance(chatRoomId: Long) : InChatFragment{
            return InChatFragment(chatRoomId)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.in_chat_fragment, container, false)

        childFragmentManager.beginTransaction()
            .add(R.id.sendChatMessageConstraintLayout,SendChatMessageFragment())
            .add(R.id.chatMessagesDisplayConstraintLayout,DisplayChatMessagesFragment(chatRoomId))
            .addToBackStack(null)
            .commit()

        return inflate
    }
}