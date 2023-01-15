package com.ashish.myapplication.domain.usecase

import com.ashish.myapplication.domain.repository.JokesRepository
import com.ashish.myapplication.domain.usecase.base.UseCase

class GetJokeFromNetworkUseCase constructor(
    private val jokesRepository: JokesRepository
) : UseCase<String, Any?>() {

    override suspend fun run(params: Any?): String {
        return jokesRepository.getJokeFromNetwork()
    }

}