package com.example.myapplication.ui.inchat

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.R
import com.example.myapplication.data.dto.ChatDto
import com.example.myapplication.databinding.ChatMessageItemBinding
import com.example.myapplication.ui.common.BaseAdapter

class ChatMessagesAdapter: BaseAdapter<ChatDto.ChatMessageDto, ChatMessageItemBinding>(diffUtil, R.layout.chat_message_item) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatDto.ChatMessageDto>() {
            override fun areContentsTheSame(oldItem: ChatDto.ChatMessageDto, newItem: ChatDto.ChatMessageDto): Boolean {
                return (oldItem.chatMessageBody==newItem.chatMessageBody)
            }

            override fun areItemsTheSame(oldItem: ChatDto.ChatMessageDto, newItem: ChatDto.ChatMessageDto): Boolean {
                return oldItem.chatMessageId == newItem.chatMessageId
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.viewBinding.chatMessage=getItem(position)
    }
}