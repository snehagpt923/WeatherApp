package com.example.weatherapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import com.example.weatherapp.model.Blog
import com.example.weatherapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MyRecyclerViewAdapter.ItemClickListener {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeObservers()
        viewModel.getBlogsFromServer()
    }

    private fun subscribeObservers() {
        viewModel.getBlogsLiveData.observe(this, {
            when (it) {
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    initViews(it.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message != null) {
            tv_error.visibility = View.GONE
            tv_error.text = message
        } else {
            tv_error.text = ""
            tv_error.visibility = View.GONE
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun initViews(blogs: List<Blog>) {
        val adapter = MyRecyclerViewAdapter(this, blogs)
        adapter.setClickListener(this)
        rv_blogs.adapter = adapter
    }

    override fun onItemClick(view: View?, blog: Blog) {
        Toast.makeText(this, blog.title, Toast.LENGTH_LONG).show()
    }
}