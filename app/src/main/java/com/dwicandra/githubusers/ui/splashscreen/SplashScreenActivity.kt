package com.dwicandra.githubusers.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dwicandra.githubusers.ui.main.MainActivity
import com.dwicandra.githubusers.databinding.ActivitySplashScreenBinding
import com.dwicandra.githubusers.ui.setting.SettingPreference
import com.dwicandra.githubusers.ui.setting.SettingViewModel
import com.dwicandra.githubusers.ui.setting.ViewModelFactory
import com.dwicandra.githubusers.ui.setting.dataStore

class SplashScreenActivity(private var binding: ActivitySplashScreenBinding) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val iv_logoSplashScreen = binding.splashscreen
        iv_logoSplashScreen.alpha = 0f
        iv_logoSplashScreen.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        val pref = SettingPreference.getInstance(dataStore)
        val settingViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}