package com.hamedsafari.filckrphotos.mockData

import com.hamedsafari.filckrphotos.data.local.dao.BookmarkDao
import com.hamedsafari.filckrphotos.data.repository.BookmarkRepository
import com.hamedsafari.filckrphotos.data.repository.mappers.toDomainModel
import com.hamedsafari.filckrphotos.data.repository.mappers.toLocalModel
import com.hamedsafari.filckrphotos.domain.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MockBookmarkRepository(
    private val dao: BookmarkDao
) : BookmarkRepository {
    override val bookmarks: Flow<List<Photo>> =
        dao.loadAllBookmarks().map { it.map { it.toDomainModel() } }

    override suspend fun toggleBookmark(photo: Photo, bookmarked: Boolean) {
        if (bookmarked)
            dao.delete(photo.id)
        else
            dao.insert(photo.toLocalModel())
    }
}
