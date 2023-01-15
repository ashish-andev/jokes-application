package com.ashish.myapplication.presentation.jokes.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ashish.myapplication.databinding.ItemJokeBinding

class JokeViewHolder(private val itemJokeBinding: ItemJokeBinding) :
    RecyclerView.ViewHolder(itemJokeBinding.root) {

    fun bind(joke: String) {
        itemJokeBinding.textviewJoke.text = joke
    }
}