package com.dicoding.githubapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubapp.databinding.ItemUserBinding

class ListAdapter(private var userList : MutableList<User>): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user: User =  userList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
    }

    override fun getItemCount(): Int = userList.size

    class ListViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind ( user: User){
            Glide.with(itemView.context)
                .load(user.avatar)
                .into(binding.imgPhoto)
            binding.tvUsername.text = user.username
            binding.tvCompany.text = user.company
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}