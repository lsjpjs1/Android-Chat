package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.ui.inchat.InChatFragment
import com.example.myapplication.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val lazyContext by lazy {
        context
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chatRoomOnClickListener = {chatRoomId:Long -> (activity as MainActivity).replaceFragment(InChatFragment.newInstance(chatRoomId))}
        val chatRoomsAdapter = ChatRoomsAdapter(chatRoomOnClickListener)

        val inflate = inflater.inflate(R.layout.main_fragment, container, false)

        inflate.chatRoomsRecyclerView.also {
            it.layoutManager = LinearLayoutManager(lazyContext, LinearLayoutManager.VERTICAL, false)
            it.setHasFixedSize(false)
            it.adapter = chatRoomsAdapter
        }

        viewModel.chatRooms.observe(viewLifecycleOwner, Observer {
            it?.let {
                chatRoomsAdapter.submitList(it.toList().map { it.second }.toMutableList())
            }
        })

        return inflate
    }


}