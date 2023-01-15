package com.ashish.myapplication.domain.usecase.base

import com.ashish.myapplication.domain.model.ApiError

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}

