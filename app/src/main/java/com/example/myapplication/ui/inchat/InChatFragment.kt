package com.example.myapplication.ui.inchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.viewmodel.InChatViewModel

class InChatFragment(val chatRoomId: Long): Fragment()  {

    companion object{
        fun newInstance(chatRoomId: Long) : InChatFragment{
            return InChatFragment(chatRoomId)
        }
    }

    private val viewModel: InChatViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.in_chat_fragment, container, false)

        viewModel.setCurrentChatRoomId(chatRoomId)
        childFragmentManager.beginTransaction()
            .add(R.id.sendChatMessageConstraintLayout,SendChatMessageFragment())
            .add(R.id.chatMessagesDisplayConstraintLayout,DisplayChatMessagesFragment(chatRoomId))
            .addToBackStack(null)
            .commit()

        return inflate
    }
}