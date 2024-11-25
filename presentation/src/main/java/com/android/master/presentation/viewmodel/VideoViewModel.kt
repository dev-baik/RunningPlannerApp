package com.android.master.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.master.domain.model.ApiResult
import com.android.master.domain.model.VideoSearchItem
import com.android.master.domain.usecase.video.SearchVideoByKeywordUseCase
import com.android.master.presentation.model.VideoSearchResponseModel
import com.android.master.presentation.model.VideoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val searchVideoUseCase: SearchVideoByKeywordUseCase
) : ViewModel() {

    private val _page = MutableStateFlow(1)

    // 네트워크 연결을 확인하기 위해 nullable 타입으로 설정
    private val _keyword = MutableStateFlow<String?>(null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isPrevPageAvailable = MutableStateFlow(false)
    val isPrevPageAvailable: StateFlow<Boolean> = _isPrevPageAvailable.asStateFlow()

    private var prevKeyword: String = ""

    @OptIn(ExperimentalCoroutinesApi::class)
    private val videoSearchResponse: Flow<VideoSearchResponseModel> =
        combine(_page, _keyword) { page, keyword ->
            page to keyword
        }.flatMapLatest { (page, keyword) ->
            if (keyword == null) return@flatMapLatest flowOf(VideoSearchResponseModel.Success(emptyList()))

            _isLoading.value = true

            searchVideoUseCase(keyword, page).map { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        val currentItems = if (page == 1) {
                            emptyList()
                        } else {
                            (videoUiState.value as VideoUiModel.VideoList).videoItem.videoItemList
                        }
                        val updatedItems = (currentItems + apiResult.data.videoItemList).distinctBy { it.id }
                        prevKeyword = keyword
                        _isPrevPageAvailable.value = true
                        VideoSearchResponseModel.Success(updatedItems)
                    }

                    // 다음 페이지가 없는 경우, 네트워크 연결 오류
                    is ApiResult.Error -> {
                        // 다음 페이지가 없는 경우, 현재 키워드에 대한 기존 videoItemList 를 전달
                        if (isPrevPageAvailable.value && prevKeyword == keyword) {
                            _isPrevPageAvailable.value = false // 다음 페이지가 재호출되는 경우를 방지
                            VideoSearchResponseModel.Success((videoUiState.value as VideoUiModel.VideoList).videoItem.videoItemList)
                        } else {
                            VideoSearchResponseModel.Error(apiResult.exception.message.toString())
                        }
                    }
                }
            }.onCompletion { _isLoading.value = false }
        }

    val videoUiState: StateFlow<VideoUiModel> = videoSearchResponse.map { response ->
        when (response) {
            is VideoSearchResponseModel.Success -> {
                if (_keyword.value != null && response.videoItem.isEmpty()) {
                    VideoUiModel.VideoNotFound(_keyword.value!!)
                } else {
                    VideoUiModel.VideoList(VideoSearchItem(response.videoItem))
                }
            }

            is VideoSearchResponseModel.Error -> VideoUiModel.Error(response.message)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = VideoUiModel.VideoList(VideoSearchItem())
    )

    fun getNextPage() {
        _page.update { it + 1 }
    }

    fun updateKeyword(newKeyword: String?) {
        _keyword.update { newKeyword }
        _page.update { 1 }
    }
}