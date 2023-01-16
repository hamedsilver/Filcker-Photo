package com.hamedsafari.filckrphotos.features.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hamedsafari.filckrphotos.data.repository.BookmarkRepository
import com.hamedsafari.filckrphotos.domain.Photo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BookmarkViewModel(
    repository: BookmarkRepository
) : ViewModel() {

    val uiState: StateFlow<BookmarksUiState> =
        repository.bookmarks.map(
            BookmarksUiState::Bookmarks
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BookmarksUiState.Loading
        )
}

sealed interface BookmarksUiState {
    object Loading : BookmarksUiState

    data class Bookmarks(
        val photos: List<Photo>
    ) : BookmarksUiState

    object Empty : BookmarksUiState
}

class BookmarkViewModelFactory(
    private val repository: BookmarkRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        BookmarkViewModel(repository) as T
}
