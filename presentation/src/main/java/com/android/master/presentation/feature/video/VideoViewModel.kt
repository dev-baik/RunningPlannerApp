package com.android.master.presentation.feature.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.android.master.domain.usecase.video.GetVideoSearchResult
import com.android.master.presentation.feature.video.model.VideoUiModel
import com.android.master.presentation.feature.video.model.VideoUiModel.Companion.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getVideoSearchResult: GetVideoSearchResult
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideoContract.VideoUiState())
    val uiState: StateFlow<VideoContract.VideoUiState>
        get() = _uiState.asStateFlow()
    val currentState: VideoContract.VideoUiState
        get() = uiState.value

    private val _sideEffect: MutableSharedFlow<VideoContract.VideoSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<VideoContract.VideoSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun setSideEffect(sideEffect: VideoContract.VideoSideEffect) {
        viewModelScope.launch { _sideEffect.emit(sideEffect) }
    }

    private val _receivedVideos = MutableStateFlow<PagingData<VideoUiModel>>(PagingData.empty())
    val receivedVideos: StateFlow<PagingData<VideoUiModel>> = _receivedVideos.asStateFlow()

    fun setEvent(event: VideoContract.VideoEvent) {
        viewModelScope.launch {
            handleEvent(event)
        }
    }

    private fun handleEvent(event: VideoContract.VideoEvent) {
        when (event) {
            is VideoContract.VideoEvent.DismissDialogVideo -> { _uiState.value = currentState.copy(isVideoDialogOpen = false) }
            is VideoContract.VideoEvent.OnDialogVideo -> { _uiState.value = currentState.copy(isVideoDialogOpen = true, url = event.url, height = event.height) }
            is VideoContract.VideoEvent.OnKeywordValueChange -> { _uiState.value = currentState.copy(keyword = event.keyword) }
            is VideoContract.VideoEvent.FetchVideos -> { _uiState.value = currentState.copy(videoLoadState = event.videoLoadState) }
        }
    }

    fun fetchVideo() {
        viewModelScope.launch {
            getVideoSearchResult(currentState.keyword)
                .cachedIn(viewModelScope)
                .map { pagingData -> pagingData.map { it.toUiModel() } }
                .collectLatest { uiModelPagingData ->
                    _receivedVideos.emit(uiModelPagingData)
                }
        }
    }
}