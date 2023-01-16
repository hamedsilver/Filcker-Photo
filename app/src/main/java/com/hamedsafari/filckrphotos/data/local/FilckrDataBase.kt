package com.hamedsafari.filckrphotos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hamedsafari.filckrphotos.data.local.dao.BookmarkDao
import com.hamedsafari.filckrphotos.data.local.dao.SearchSuggestionDao
import com.hamedsafari.filckrphotos.data.local.model.BookmarkedPhotoEntity
import com.hamedsafari.filckrphotos.data.local.model.SearchSuggestionEntity

@Database(entities = [SearchSuggestionEntity::class, BookmarkedPhotoEntity::class], version = 1, exportSchema = false)
abstract class FilckrDataBase : RoomDatabase() {

    // DAO
    abstract fun searchDao(): SearchSuggestionDao
    abstract fun bookmarkedDao(): BookmarkDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FilckrDataBase::class.java,
                "FilckrPhoto.db"
            )
                .build()
    }
}
