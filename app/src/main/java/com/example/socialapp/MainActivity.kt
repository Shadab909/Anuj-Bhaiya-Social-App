package com.example.socialapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.socialapp.adapter.PostAdapter
import com.example.socialapp.adapter.PostAdapterInterface
import com.example.socialapp.dao.PostDao
import com.example.socialapp.databinding.ActivityMainBinding
import com.example.socialapp.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query


class MainActivity : AppCompatActivity(), PostAdapterInterface {
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: PostAdapter
    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        postDao = PostDao()

        binding.fabBtn.setOnClickListener {
            startActivity(Intent(this,CreatePostActivity::class.java))
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val postCollections = postDao.postCollection
        val query = postCollections.orderBy("createdAt",Query.Direction.DESCENDING)
        val firebaseRecyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        adapter = PostAdapter(firebaseRecyclerViewOptions,this)
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}

