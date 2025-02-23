package com.android.master.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.master.domain.model.ApiResult
import com.android.master.domain.model.VideoItem
import com.android.master.domain.usecase.video.SearchVideoByKeywordUseCase
import com.android.master.presentation.intent.VideoIntent
import com.android.master.presentation.state.VideoViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val searchVideoUseCase: SearchVideoByKeywordUseCase
) : ViewModel() {

    private val channel = Channel<VideoIntent>()

    private val _state = MutableStateFlow<VideoViewState>(VideoViewState.Idle)
    val state: StateFlow<VideoViewState> = _state.asStateFlow()

    private var currentPage = 1
    private var currentKeyword: String? = null
    private var currentVideoItems: List<VideoItem> = emptyList()
    private var isPrevPageAvailable: Boolean = false

    init {
        handleIntents()
    }

    fun sendIntent(intent: VideoIntent) {
        viewModelScope.launch { channel.send(intent) }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            channel.consumeAsFlow().collectLatest { intent ->
                when (intent) {
                    is VideoIntent.UpdateKeyword -> {
                        currentPage = 1
                        currentKeyword = intent.keyword
                        currentVideoItems = emptyList()
                        fetchVideos()
                    }

                    is VideoIntent.LoadNextPage -> {
                        if (isPrevPageAvailable) {
                            currentPage++
                            fetchVideos()
                        }
                    }

                    is VideoIntent.Retry -> {
                        fetchVideos()
                    }
                }
            }
        }
    }

    private fun fetchVideos() {
        val keyword = currentKeyword
        if (keyword.isNullOrEmpty()) {
            _state.value = VideoViewState.Idle
            return
        }

        viewModelScope.launch {
            _state.value = VideoViewState.Loading(
                previousItems = if (currentPage == 1) emptyList() else currentVideoItems,
                keyword = keyword
            )

            searchVideoUseCase(keyword, currentPage).collectLatest { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        val newItems = apiResult.data.videoItemList
                        val updatedItems = if (currentPage == 1) {
                            newItems
                        } else {
                            (currentVideoItems + newItems).distinctBy { it.id }
                        }
                        currentVideoItems = updatedItems
                        isPrevPageAvailable = true

                        if (updatedItems.isEmpty() && currentPage == 1) {
                            _state.value = VideoViewState.NotFound(keyword)
                        } else {
                            _state.value = VideoViewState.Success(
                                videoItems = updatedItems,
                                keyword = keyword,
                                isPrevPageAvailable = isPrevPageAvailable
                            )
                        }
                    }

                    is ApiResult.Error -> {
                        if (isPrevPageAvailable && currentKeyword == keyword && currentPage > 1) {
                            isPrevPageAvailable = false
                            _state.value = VideoViewState.Success(
                                videoItems = currentVideoItems,
                                keyword = keyword,
                                isPrevPageAvailable = isPrevPageAvailable
                            )
                        } else {
                            _state.value = VideoViewState.Error(
                                message = apiResult.exception.message ?: "Unknown error"
                            )
                        }
                    }
                }
            }
        }
    }
}