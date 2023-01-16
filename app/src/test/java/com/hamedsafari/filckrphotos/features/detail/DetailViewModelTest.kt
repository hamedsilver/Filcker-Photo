package com.hamedsafari.filckrphotos.features.detail

import com.hamedsafari.filckrphotos.data.local.dao.BookmarkDao
import com.hamedsafari.filckrphotos.data.repository.BookmarkRepository
import com.hamedsafari.filckrphotos.domain.Photo
import com.hamedsafari.filckrphotos.mockData.MockBookmarkDao
import com.hamedsafari.filckrphotos.mockData.MockBookmarkRepository
import com.hamedsafari.filckrphotos.rules.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var dao: BookmarkDao
    private lateinit var repository: BookmarkRepository
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        dao = MockBookmarkDao()
        repository = MockBookmarkRepository(dao)
        viewModel = DetailViewModel(id = "0", repository = repository)
    }

    @Test
    fun uiStateTopic_whenInitialized_thenIdeal() = runTest {
        assertEquals(BookmarkUiState.Ideal(), viewModel.uiState.value)
    }

    @Test
    fun uiStateTopic_whenToggleBookmark_thenIdeal_true() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        repository.toggleBookmark(photo = Photo(id = "0", "","",""), bookmarked = false)
        assertEquals(BookmarkUiState.Ideal(isBookmarked = true), viewModel.uiState.value)

        collectJob.cancel()
    }

    @Test
    fun uiStateTopic_whenToggleBookmark_thenIdeal_false() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        repository.toggleBookmark(photo = Photo(id = "0", "","",""), bookmarked = false)
        repository.toggleBookmark(photo = Photo(id = "0", "","",""), bookmarked = true)
        assertEquals(BookmarkUiState.Ideal(isBookmarked = false), viewModel.uiState.value)

        collectJob.cancel()
    }
}
