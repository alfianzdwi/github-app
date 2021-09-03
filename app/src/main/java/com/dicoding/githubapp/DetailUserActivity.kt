package com.dicoding.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.githubapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object{
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.detail) // Mengakses resource string.xml dari activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userGit = intent.getParcelableExtra<User>(EXTRA_USER) as User

        binding.imgProfile.setImageResource(userGit.avatar)
        binding.tvUsername.text = userGit.username
        binding.tvName.text = userGit.name
        binding.tvLocation.text = userGit.location
        binding.tvRepository.text = userGit.repository
        binding.tvCompany.text = userGit.company
        binding.tvFollowers.text = userGit.followers
        binding.tvFollowing.text = userGit.following

    }
}