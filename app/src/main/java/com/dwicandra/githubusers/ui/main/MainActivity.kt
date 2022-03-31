package com.dwicandra.githubusers.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwicandra.githubusers.ui.favorite.FavoriteActivity
import com.dwicandra.githubusers.R
import com.dwicandra.githubusers.databinding.ActivityMainBinding
import com.dwicandra.githubusers.ui.main.adapter.ListUserAdapter
import com.dwicandra.githubusers.ui.main.viewmodel.MainViewModel
import com.dwicandra.githubusers.ui.main.viewmodel.ViewModelFactory
import com.dwicandra.githubusers.ui.setting.SettingActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivSearch.setOnClickListener(this)

        initializedAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.theme -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.setting -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializedAdapter() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        observer()
    }


    private fun observer() {
        mainViewModel.apply {
            listGithubUsersLiveData?.observe(this@MainActivity) { listUser ->
                if (listUser != null) {
                    val adapter = ListUserAdapter(listUser)
                    binding.rvUsers.adapter = adapter
                    binding.emptyTxt.visibility = View.GONE
                    showLoading(false)
                } else {
                    showLoading(true)
                }
            }
            isLoading.observe(this@MainActivity) {
                showLoading(it)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_search -> {
                showLoading(false)
                val query = binding.etSearch.text.toString()
                if (query.isEmpty()) {
                    binding.emptyTxt.visibility = View.VISIBLE
                    binding.emptyTxt.text = resources.getString(R.string.searchIsEmpty)
                    binding.rvUsers.visibility = View.GONE
                }
                mainViewModel.getSearchUsers(query)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}