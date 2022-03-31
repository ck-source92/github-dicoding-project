package com.dwicandra.githubusers.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwicandra.githubusers.databinding.ActivityFavoriteBinding
import com.dwicandra.githubusers.ui.detail.adapter.FavoriteUserAdapter
import com.dwicandra.githubusers.ui.main.viewmodel.MainViewModel
import com.dwicandra.githubusers.ui.main.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val mainViewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initializedAdapter()
    }

    private fun initializedAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        observer()
    }

    private fun observer() {
        mainViewModel.apply {
            getAllFavoriteUsers().observe(this@FavoriteActivity) { listFavorite ->
                if (listFavorite != null) {
                    val adapter = FavoriteUserAdapter(listFavorite)
                    binding.rvFavorite.adapter = adapter
                }
            }
        }
    }
}