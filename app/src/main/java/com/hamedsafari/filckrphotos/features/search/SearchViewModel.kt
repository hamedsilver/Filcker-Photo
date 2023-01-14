package com.hamedsafari.filckrphotos.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hamedsafari.filckrphotos.data.repository.PhotosRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: PhotosRepository
) : ViewModel() {

    val searchTerms = MutableStateFlow("")
    private val viewModelState = MutableStateFlow(SearchViewModelState(isLoading = false))

    // UI state exposed to the UI
    val uiState = viewModelState
        .map(SearchViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        handleIntent()
    }

    @OptIn(FlowPreview::class)
    private fun handleIntent() = viewModelScope.launch {
        searchTerms
            .debounce(800)
            .filter { query ->
                if (query.isEmpty()) {
                    viewModelState.update { it.copy(photos = emptyList()) }
                    return@filter false
                } else
                    return@filter true
            }
            .distinctUntilChanged()
            .onEach { query ->
                viewModelState.update { it.copy(photos = emptyList(), isLoading = true) }
                repository.search(query).mapOnSuccess { photos ->
                    viewModelState.update { it.copy(photos = photos, isLoading = false) }
                }.onError { error ->
                    viewModelState.update { it.copy(errorMessages = error.message.orEmpty(), isLoading = false) }
                }
            }.collect()
    }

}

class SearchViewModelFactory(private val repository: PhotosRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        SearchViewModel(repository) as T
}