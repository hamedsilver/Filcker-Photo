package com.hamedsafari.filckrphotos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hamedsafari.filckrphotos.data.local.model.BookmarkedPhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg photos: BookmarkedPhotoEntity)

    @Query("DELETE FROM bookmarked_photo WHERE id = :id")
    fun delete(id: String)

    @Query("SELECT * FROM bookmarked_photo")
    fun loadAllBookmarks(): Flow<List<BookmarkedPhotoEntity>>
}
