package com.hamedsafari.filckrphotos.features.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hamedsafari.filckrphotos.data.repository.BookmarkRepository
import com.hamedsafari.filckrphotos.domain.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    id: String,
    private val repository: BookmarkRepository
) : ViewModel() {

    val uiState: StateFlow<BookmarkUiState> = toUiState(
        id = id,
        repository = repository,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BookmarkUiState.Ideal()
    )

    fun toggleBookmark(photo: Photo, bookmarked: Boolean) = viewModelScope.launch {
        repository.toggleBookmark(photo, bookmarked)
    }

}

sealed interface BookmarkUiState {
    data class Ideal(val isBookmarked: Boolean = false) : BookmarkUiState
}

private fun toUiState(id: String, repository: BookmarkRepository): Flow<BookmarkUiState> =
    repository.bookmarks.map {
        it.map { it.id }.contains(id)
    }.map { BookmarkUiState.Ideal(isBookmarked = it) }


class DetailViewModelFactory(
    private val id: String,
    private val repository: BookmarkRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetailViewModel(id, repository) as T
}
