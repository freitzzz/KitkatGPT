package com.github.freitzzz.kitkatgpt.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.freitzzz.kitkatgpt.R
import com.github.freitzzz.kitkatgpt.data.model.Message
import com.github.freitzzz.kitkatgpt.viewmodel.ConversationViewModel


class Conversation : Fragment() {
    private lateinit var viewModel: ConversationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_conversation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[ConversationViewModel::class.java]
        this.viewModel = viewModel

        val recyclerView = view.findViewById<RecyclerView>(R.id.fragment_conversation_recycler_view)
        val adapter = MessagesAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        viewModel.messages().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.post(it)
            }
        }
    }
}

class MessagesAdapter : RecyclerView.Adapter<MessageViewHolder>() {
    private val data = arrayListOf<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_message_bubble, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun post(message: Message) {
        data.add(message)
        notifyItemChanged(data.size - 1)
    }
}

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(message: Message?) {
        if (message != null) {
            itemView.findViewById<TextView>(R.id.message_bubble_text).text = message.value
        }
    }
}