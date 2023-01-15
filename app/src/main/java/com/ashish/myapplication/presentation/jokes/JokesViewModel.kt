package com.ashish.myapplication.presentation.jokes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.myapplication.domain.model.ApiError
import com.ashish.myapplication.domain.usecase.AddJokesToCacheUseCase
import com.ashish.myapplication.domain.usecase.GetCachedJokesUseCase
import com.ashish.myapplication.domain.usecase.GetJokeFromNetworkUseCase
import com.ashish.myapplication.domain.usecase.base.UseCaseResponse
import com.ashish.myapplication.utils.NetworkConnectivity
import com.ashish.myapplication.utils.SingleEvent
import kotlinx.coroutines.*

class JokesViewModel constructor(
    private val getJokeFromNetworkUseCase: GetJokeFromNetworkUseCase,
    private val getCachedJokesUseCase: GetCachedJokesUseCase,
    private val addJokesToCacheUseCase: AddJokesToCacheUseCase,
    private val networkConnectivity: NetworkConnectivity
) : ViewModel() {
    private var getJokePollingJob: Job? = null

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     * To prevent external write
     */
    private val jokesLiveDataPrivate = MutableLiveData<List<String>>()
    val jokesLiveData: LiveData<List<String>> get() = jokesLiveDataPrivate

    private val showErrorPrivate = MutableLiveData<SingleEvent<Any>>()
    val showError: LiveData<SingleEvent<Any>> get() = showErrorPrivate

    init {
        getCachedJokes()
        getJokePollingJob = getJokePollingRequest()
    }

    private fun getJokePollingRequest(): Job {
        return viewModelScope.launch {
            while (isActive) {
                getJokeFromNetwork()
                delay(60_000)
            }
        }
    }

    private fun getJokeFromNetwork() {
        if (networkConnectivity.isConnected()) {
            getJokeFromNetworkUseCase.invoke(
                viewModelScope,
                null,
                object : UseCaseResponse<String> {
                    override fun onSuccess(result: String) {
                        jokesLiveDataPrivate.value =
                            jokesLiveDataPrivate.value?.takeLast(9)?.plus(result) ?: listOf(result)
                    }

                    override fun onError(apiError: ApiError?) {
                        showErrorMessage(apiError)
                    }
                })
        } else {
            showErrorMessage(ApiError(null, errorStatus = ApiError.ErrorStatus.NO_CONNECTION))
        }

    }


    private fun getCachedJokes() {
        getCachedJokesUseCase.invoke(
            viewModelScope,
            null,
            object : UseCaseResponse<List<String>> {
                override fun onSuccess(result: List<String>) {
                    jokesLiveDataPrivate.value = result
                }

                override fun onError(apiError: ApiError?) {
                    showErrorMessage(apiError)
                }

            })
    }

    fun showErrorMessage(apiError: ApiError?) {
        apiError?.getErrorMessage()?.let {
            showErrorPrivate.value = SingleEvent(it)
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        getJokePollingJob?.cancel()
        addJokesToCache()
        super.onCleared()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun addJokesToCache() {
        jokesLiveDataPrivate.value?.let {
            addJokesToCacheUseCase.invoke(GlobalScope, it, null)
        }
    }
}