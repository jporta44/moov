package com.moov.moovapp.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moov.moovapp.databinding.UserListContentBinding
import com.moov.moovapp.model.User

/**
 * Adapter that supports [PagingData]
 */
class UserAdapter(diffCallback: DiffUtil.ItemCallback<User>):
    PagingDataAdapter<User, UserAdapter.UserViewHolder>(diffCallback) {

    var onItemClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            UserListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.idView.text = item?.id.toString()
        holder.nameView.text = item?.getFullName()
        holder.avatarView?.let {
            Glide.with(it)
                .load(item?.avatar)
                .placeholder(ColorDrawable(Color.GRAY))
                .into(it)
        }

    }

    inner class UserViewHolder(binding: UserListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.idText
        val nameView: TextView = binding.nameTxt
        val avatarView: ImageView? = binding.avatar

        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { user -> onItemClick?.invoke(user) }
            }
        }
    }

    object UserComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

}