package com.example.socialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.socialapp.dao.PostDao
import com.example.socialapp.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding
    private lateinit var postDao : PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_post)
        postDao = PostDao()
        binding.postBtn.setOnClickListener {
            val input = binding.postInput.text.toString().trim()
            if (input.isNotEmpty()){
                postDao.addPost(input)
                finish()
            }
        }
    }
}