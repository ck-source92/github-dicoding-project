package com.dwicandra.githubusers.ui.detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwicandra.githubusers.R
import com.dwicandra.githubusers.databinding.FragmentFollowBinding
import com.dwicandra.githubusers.ui.detail.DetailActivity
import com.dwicandra.githubusers.ui.main.adapter.ListUserAdapter
import com.dwicandra.githubusers.ui.main.viewmodel.MainViewModel
import com.dwicandra.githubusers.ui.main.viewmodel.ViewModelFactory

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(
            requireActivity()
        )
    }

    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg = arguments
        username = arg?.getString(DetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        initializeAdapter()
        mainViewModel.getFollower(username)

    }
    private fun initializeAdapter() {
        val layoutManager = LinearLayoutManager(activity)
        binding?.rvFollowers?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding?.rvFollowers?.addItemDecoration(itemDecoration)

        observeData()
    }

    private fun observeData() {
        mainViewModel.apply {
            listFollowersGithubUsersLiveData?.observe(viewLifecycleOwner) {
                if (it != null) {
                    val adapter = ListUserAdapter(it)
                    binding?.rvFollowers?.adapter = adapter
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

}