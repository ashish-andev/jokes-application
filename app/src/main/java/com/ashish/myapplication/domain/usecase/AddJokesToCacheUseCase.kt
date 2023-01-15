package com.ashish.myapplication.domain.usecase

import com.ashish.myapplication.domain.repository.JokesRepository
import com.ashish.myapplication.domain.usecase.base.UseCase

class AddJokesToCacheUseCase constructor(
    private val jokesRepository: JokesRepository
) : UseCase<Unit, List<String>>() {

    override suspend fun run(params: List<String>?) {
        params?.let { jokesRepository.addJokesToCache(it) }
    }

}