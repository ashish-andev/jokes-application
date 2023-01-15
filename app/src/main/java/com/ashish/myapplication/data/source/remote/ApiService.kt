package com.ashish.myapplication.data.source.remote

import retrofit2.http.GET

interface ApiService {
    @GET("/api")
    suspend fun getJoke(): String
}