package com.example.myapplication.ui.inchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.viewmodel.InChatViewModel
import com.example.myapplication.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.display_chat_messages_fragment.view.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class DisplayChatMessagesFragment(val chatRoomId: Long): Fragment() {

    private val lazyContext by lazy {
        context
    }
    private val viewModel: InChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.display_chat_messages_fragment, container, false)
        val chatMessagesAdapter = ChatMessagesAdapter()
        inflate.chatMessagesRecyclerView.also {
            it.layoutManager = LinearLayoutManager(lazyContext, LinearLayoutManager.VERTICAL, false)
            it.setHasFixedSize(false)
            it.adapter = chatMessagesAdapter
        }
        viewModel.getChatMessages(chatRoomId)
        viewModel.chatMessages.observe(viewLifecycleOwner, Observer {
            it?.let {
                chatMessagesAdapter.submitList(it.toList())
            }
        })

        return inflate
    }
}