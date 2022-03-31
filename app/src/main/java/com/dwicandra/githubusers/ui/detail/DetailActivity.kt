package com.dwicandra.githubusers.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dwicandra.githubusers.R
import com.dwicandra.githubusers.data.local.entity.UserEntity
import com.dwicandra.githubusers.databinding.ActivityDetailBinding
import com.dwicandra.githubusers.ui.detail.adapter.sectionpager.SectionPagerAdapter
import com.dwicandra.githubusers.ui.main.viewmodel.MainViewModel
import com.dwicandra.githubusers.ui.main.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val mainViewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(this) }

    private var userEntity: UserEntity? = null
    private var username: String? = null

    private var state: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        username = intent.getStringExtra(EXTRA_USERNAME)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val tabLayout: TabLayout = binding.tabsLayout
        val viewPager: ViewPager2 = binding.viewPager

        val sectionPagerAdapter = SectionPagerAdapter(supportFragmentManager, lifecycle, bundle)
        viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        if (username != null) {
            mainViewModel.getDetailUsers(username!!)
        }
        setDetailUser()

        binding.fabDetail.setOnClickListener {
            if (state) {
                setImageFavorite(false)
                mainViewModel.deleteFavoriteUser(userEntity!!)
                showSnackBar(it, "Remove to Favorite")
            } else {
                setImageFavorite(true)
                mainViewModel.setFavoriteUser(userEntity!!)
                showSnackBar(it, "Add To Favorite")
            }
        }
    }

    private fun setDetailUser() {
        mainViewModel.apply {
            detailGithubUserLiveData?.observe(this@DetailActivity) {
                if (it != null) {
                    binding.apply {
                        Glide.with(this@DetailActivity)
                            .load(it.avatarUrl)
                            .circleCrop()
                            .error(R.mipmap.ic_launcher)
                            .into(ivAvatar)
                        tvName.text = it.name
                        tvUsername.text = it.login
                        tvCompany.text = it.company
                        tvLocation.text = it.location
                        tvRepository.text = it.publicRepos.toString()
                        tvFollowers.text = it.followers.toString()
                        tvFollowing.text = it.following.toString()

                        userEntity = UserEntity(it.login, it.avatarUrl)
                        actionBar.apply {
                            title = it.name
                        }
                    }
                }
            }
            checkUser(username.toString())
            isFavorite.observe(this@DetailActivity) {
                setImageFavorite(it)
                state = it
            }

        }
    }

    fun setImageFavorite(state: Boolean) {
        binding.fabDetail.apply {
            if (state) setImageResource(R.drawable.ic_baseline_favorite_24) else setImageResource(
                R.drawable.ic_baseline_favorite_border_24
            )
        }
    }

    private fun showSnackBar(view: View, message: String?) {
        val snackBar = Snackbar.make(view, message!!, Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view
        val txt =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow_700))
        txt.setTextColor(ContextCompat.getColor(this, R.color.black_200))

        snackBar.show()
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = arrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}