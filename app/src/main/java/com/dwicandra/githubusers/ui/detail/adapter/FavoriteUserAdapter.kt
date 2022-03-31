package com.dwicandra.githubusers.ui.detail.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwicandra.githubusers.R
import com.dwicandra.githubusers.data.local.entity.UserEntity
import com.dwicandra.githubusers.databinding.ItemUserBinding
import com.dwicandra.githubusers.ui.detail.DetailActivity

class FavoriteUserAdapter(private val listFavoriteUser: List<UserEntity>) :
    RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(listFavoriteUser[position].avatar)
                .circleCrop()
                .error(R.mipmap.ic_launcher)
                .into(ivAvatar)
            tvName.text = listFavoriteUser[position].username
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, listFavoriteUser[position].username)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listFavoriteUser.size

}