package com.ashish.myapplication.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ashish.myapplication.data.source.local.entity.JokesEntity

@Dao
interface JokesDao {

    @Transaction
    suspend fun clearAndInsert(jokesEntity: List<JokesEntity>) {
        clear()
        insert(jokesEntity)
    }

    @Insert
    suspend fun insert(jokesEntity: List<JokesEntity>)

    @Query("SELECT * FROM jokes ORDER BY id desc")
    suspend fun getJokes(): List<JokesEntity>

    @Query("DELETE FROM jokes")
    suspend fun clear()
}