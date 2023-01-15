package com.hamedsafari.filckrphotos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hamedsafari.filckrphotos.data.local.model.SearchSuggestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchSuggestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchTerms(vararg terms: SearchSuggestionEntity)

    @Query("SELECT * FROM search_suggestion ORDER BY id DESC LIMIT 5")
    fun loadAllSearchTerms(): Flow<List<SearchSuggestionEntity>>
}
