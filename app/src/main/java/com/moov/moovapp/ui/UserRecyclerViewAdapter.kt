package com.moov.moovapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moov.moovapp.databinding.UserListContentBinding
import com.moov.moovapp.model.User
import java.util.ArrayList

class UserRecyclerViewAdapter: RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {

    private var values: ArrayList<User> = ArrayList()
    var onItemClick: ((User) -> Unit)? = null

    fun addUsers (users: List<User>) {
        values.clear()
        values.addAll(users)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            UserListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id.toString()
        holder.contentView.text = item.getFullName()
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: UserListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.idText
        val contentView: TextView = binding.content

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(values[bindingAdapterPosition])
            }
        }
    }

}