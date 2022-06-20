package com.example.myapplication.ui.inchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.databinding.SendChatMessageFragmentBinding
import com.example.myapplication.viewmodel.InChatViewModel

class SendChatMessageFragment:Fragment() {
    private val viewModel: InChatViewModel by viewModels(
        ownerProducer = {requireParentFragment()}
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<SendChatMessageFragmentBinding>(
            inflater,
            R.layout.send_chat_message_fragment,
            container,
            false
        )
        binding.viewModel = viewModel


        return binding.root
    }
}