package com.hamedsafari.filckrphotos.mockData

import com.hamedsafari.filckrphotos.data.local.dao.BookmarkDao
import com.hamedsafari.filckrphotos.data.local.model.BookmarkedPhotoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MockBookmarkDao : BookmarkDao {

    private val mockDB = MutableStateFlow(emptyList<BookmarkedPhotoEntity>())

    override fun insert(vararg photos: BookmarkedPhotoEntity) {
        mockDB.update {
            it.plus(photos)
        }
    }

    override fun delete(id: String) {
        mockDB.update {
            val deletable = it.first { it.id == id }
            it.minus(deletable)
        }
    }

    override fun loadAllBookmarks(): Flow<List<BookmarkedPhotoEntity>> = mockDB
}
