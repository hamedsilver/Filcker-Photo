package com.hamedsafari.filckrphotos.data.repository

import com.hamedsafari.filckrphotos.data.local.dao.BookmarkDao
import com.hamedsafari.filckrphotos.domain.Photo
import com.hamedsafari.filckrphotos.mockData.MockBookmarkDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookmarkRepositoryImplTest{
    private lateinit var subject: BookmarkRepository
    private lateinit var dao: BookmarkDao

    @Before
    fun setUp() {
        dao = MockBookmarkDao()
        subject = BookmarkRepositoryImpl(dao)
    }

    @Test
    fun bookmarkRepository_toggle_bookmark_logic_verify() =
        runTest {
            //add two item to dao
            subject.toggleBookmark(photo = Photo(id = "0", "","",""), bookmarked = false)
            subject.toggleBookmark(photo = Photo(id = "1", "","",""), bookmarked = false)

            assertEquals(
                listOf("0", "1"),
                subject.bookmarks
                    .map { it.map { it.id } }
                    .first()
            )

            assertEquals(
                dao.loadAllBookmarks()
                    .map { it.map { it.id } }
                    .first(),
                subject.bookmarks
                    .map { it.map { it.id } }
                    .first()
            )
        }

    @Test
    fun bookmarkRepository_set_bookmarked_photo_logic_delegates_to_dao() =
        runTest {
            //add two item to dao
            subject.toggleBookmark(photo = Photo(id = "0", "","",""), bookmarked = false)
            subject.toggleBookmark(photo = Photo(id = "1", "","",""), bookmarked = false)

            assertEquals(
                listOf("0", "1"),
                subject.bookmarks
                    .map { it.map { it.id } }
                    .first()
            )

            //remove one item of dao
            subject.toggleBookmark(photo = Photo(id = "0", "","",""), bookmarked = true)

            assertEquals(
                listOf("1"),
                subject.bookmarks
                    .map { it.map { it.id } }
                    .first()
            )

            assertEquals(
                dao.loadAllBookmarks()
                    .map { it.map { it.id } }
                    .first(),
                subject.bookmarks
                    .map { it.map { it.id } }
                    .first()
            )
        }

    @Test
    fun bookmarkRepository_toggle_bookmark_photo_logic_delegates_to_dao() =
        runTest {
            //add one item to dao
            subject.toggleBookmark(photo = Photo(id = "0", "","",""), bookmarked = false)

            assertEquals(
                listOf("0"),
                subject.bookmarks
                    .map { it.map { it.id } }
                    .first()
            )

            //add one item to dao
            subject.toggleBookmark(photo = Photo(id = "1", "","",""), bookmarked = false)

            assertEquals(
                listOf("0", "1"),
                subject.bookmarks
                    .map { it.map { it.id } }
                    .first()
            )

            assertEquals(
                dao.loadAllBookmarks()
                    .map { it.map { it.id } }
                    .first(),
                subject.bookmarks
                    .map { it.map { it.id } }
                    .first()
            )
        }

}
