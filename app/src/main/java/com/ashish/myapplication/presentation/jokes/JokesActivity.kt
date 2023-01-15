package com.ashish.myapplication.presentation.jokes

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashish.myapplication.databinding.ActivityJokesBinding
import com.ashish.myapplication.ext.setupSnackbar
import com.ashish.myapplication.presentation.base.BaseActivity
import com.ashish.myapplication.presentation.jokes.adapter.JokesAdapter
import com.ashish.myapplication.utils.SingleEvent
import com.google.android.material.snackbar.Snackbar
import com.ashish.myapplication.ext.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class JokesActivity : BaseActivity() {

    private lateinit var binding: ActivityJokesBinding
    private val viewModel: JokesViewModel by viewModel()
    private var jokesAdapter: JokesAdapter? = JokesAdapter()


    override fun initViewBinding() {
        binding = ActivityJokesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.adapter = jokesAdapter
    }

    override fun observeViewModel() {
        observe(viewModel.jokesLiveData, ::handleJokesList)
        observeToast(viewModel.showError)
    }

    private fun handleJokesList(jokes: List<String>) {
        jokesAdapter?.jokes = jokes
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }
}