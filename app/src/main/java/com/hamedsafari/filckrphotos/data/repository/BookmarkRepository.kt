package com.hamedsafari.filckrphotos.data.repository

import com.hamedsafari.filckrphotos.data.local.dao.BookmarkDao
import com.hamedsafari.filckrphotos.data.repository.mappers.toDomainModel
import com.hamedsafari.filckrphotos.data.repository.mappers.toLocalModel
import com.hamedsafari.filckrphotos.domain.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * An interface to represent a repository that returns Bookmarks,
 * and save bookmarks.
 */
interface BookmarkRepository {

    /**
     * Stream of List[Photo].
     *
     * Assumptions:
     *  - List of bookmarked photos returned.
     */
    val bookmarks: Flow<List<Photo>>

    /**
     * Assumptions:
     *  - Toggles photos bookmark/unmarked.
     */
    suspend fun toggleBookmark(photo: Photo, bookmarked: Boolean)
}

/**
 *  An implementation for the BookmarkRepository
 */
internal class BookmarkRepositoryImpl(
    private val dao: BookmarkDao,
) : BookmarkRepository {

    override val bookmarks: Flow<List<Photo>> =
        dao.loadAllBookmarks().map { it.map { it.toDomainModel() } }

    override suspend fun toggleBookmark(photo: Photo, bookmarked: Boolean) {
        withContext(Dispatchers.IO) {
            if (bookmarked)
                dao.delete(photo.id)
            else
                dao.insert(photo.toLocalModel())
        }
    }
}
