package com.ashish.myapplication.presentation.jokes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashish.myapplication.databinding.ItemJokeBinding
import kotlin.properties.Delegates

class JokesAdapter : RecyclerView.Adapter<JokeViewHolder>() {

    var jokes: List<String> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val itemBinding =
            ItemJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(jokeViewHolder: JokeViewHolder, position: Int) {
        jokeViewHolder.bind(jokes[position])

    }

    override fun getItemCount(): Int {
        return jokes.size
    }
}