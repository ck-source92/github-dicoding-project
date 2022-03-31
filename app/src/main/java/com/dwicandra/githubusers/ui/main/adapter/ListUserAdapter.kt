package com.dwicandra.githubusers.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwicandra.githubusers.R
import com.dwicandra.githubusers.data.local.entity.UserEntity
import com.dwicandra.githubusers.data.remote.response.ItemsItem
import com.dwicandra.githubusers.databinding.ItemUserBinding
import com.dwicandra.githubusers.ui.detail.DetailActivity

class ListUserAdapter(private val listUser: ArrayList<ItemsItem>) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(listUser[position].avatarUrl)
                .circleCrop()
                .error(R.mipmap.ic_launcher)
                .into(ivAvatar)
            tvName.text = listUser[position].login
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, listUser[position].login)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = listUser.size

}