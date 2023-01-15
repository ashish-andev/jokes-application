package com.ashish.myapplication.domain.repository

interface JokesRepository {
    suspend fun getCachedJokes(): List<String>
    suspend fun addJokesToCache(jokes: List<String>)
    suspend fun getJokeFromNetwork(): String
}