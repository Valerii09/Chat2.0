package com.example.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val currentUserId: String) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    private val messages = mutableListOf<Message>()


    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.messageTextView.text = message.messageText

        if (message.senderId == currentUserId) {
            // Отправитель - отображается справа
            holder.messageTextView.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            // Здесь вы можете также применить другие стили, чтобы сообщение выглядело справа
        } else {
            // Получатель - отображается слева
            holder.messageTextView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            // Здесь вы можете также применить другие стили, чтобы сообщение выглядело слева
        }
    }


    override fun getItemCount(): Int {
        return messages.size
    }

    fun addMessage(message: Message, senderId: String) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }



}
