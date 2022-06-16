package com.example.myapplication.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.R
import com.example.myapplication.data.dto.ChatDto
import com.example.myapplication.databinding.ChatRoomItemBinding
import com.example.myapplication.ui.common.BaseAdapter

class ChatRoomsAdapter : BaseAdapter<ChatDto.ChatRoomDto,ChatRoomItemBinding>(diffUtil, R.layout.chat_room_item){

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatDto.ChatRoomDto>() {
            override fun areContentsTheSame(oldItem: ChatDto.ChatRoomDto, newItem: ChatDto.ChatRoomDto): Boolean {
                return oldItem.recentMessageTime == newItem.recentMessageTime
            }

            override fun areItemsTheSame(oldItem: ChatDto.ChatRoomDto, newItem: ChatDto.ChatRoomDto): Boolean {
                return oldItem.chatRoomId == newItem.chatRoomId
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.viewBinding.chatRoomDto = getItem(position)

    }
}