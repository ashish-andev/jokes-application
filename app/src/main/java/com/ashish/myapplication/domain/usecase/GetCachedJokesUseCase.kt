package com.ashish.myapplication.domain.usecase

import com.ashish.myapplication.domain.repository.JokesRepository
import com.ashish.myapplication.domain.usecase.base.UseCase

class GetCachedJokesUseCase constructor(
    private val jokesRepository: JokesRepository
) : UseCase<List<String>, Any?>() {

    override suspend fun run(params: Any?): List<String> {
        return jokesRepository.getCachedJokes()
    }

}