package com.ashish.myapplication.data.repository

import com.ashish.myapplication.data.source.local.db.AppDatabase
import com.ashish.myapplication.data.source.local.entity.JokesEntity
import com.ashish.myapplication.data.source.remote.ApiService
import com.ashish.myapplication.domain.repository.JokesRepository

class JokesRepositoryImpl(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : JokesRepository {

    override suspend fun getCachedJokes(): List<String> {
        return appDatabase.jokesDao().getJokes().map { it.text }
    }

    override suspend fun addJokesToCache(jokes: List<String>) {
        val jokesEntities = jokes.map { JokesEntity(text = it) }
        appDatabase.jokesDao().clearAndInsert(jokesEntities)
    }

    override suspend fun getJokeFromNetwork(): String {
        return apiService.getJoke()
    }
}