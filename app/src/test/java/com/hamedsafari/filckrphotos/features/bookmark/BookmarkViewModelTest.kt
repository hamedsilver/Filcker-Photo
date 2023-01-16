package com.hamedsafari.filckrphotos.features.bookmark

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
class BookmarkViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var dao: BookmarkDao
    private lateinit var repository: BookmarkRepository
    private lateinit var viewModel: BookmarkViewModel

    @Before
    fun setup() {
        dao = MockBookmarkDao()
        repository = MockBookmarkRepository(dao)
        viewModel = BookmarkViewModel(repository = repository)
    }

    @Test
    fun uiState_whenInitialized_thenShowLoading() = runTest {
        assertEquals(BookmarksUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun uiState_whenBookmarkPhoto_thenShowUpdatedPhotos() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        val mockPhoto = Photo(id = "0", "", "", "")
        repository.toggleBookmark(photo = mockPhoto, bookmarked = false)

        assertEquals(
            true,
            (viewModel.uiState.value as BookmarksUiState.Bookmarks)
                .photos.contains(mockPhoto)
        )

        assertEquals(
            BookmarksUiState.Bookmarks(photos = listOf(mockPhoto)),
            viewModel.uiState.value
        )

        collectJob.cancel()
    }
}
