package com.hamedsafari.filckrphotos.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "search_suggestion", indices = [Index(value = ["searchTerm"], unique = true)])
data class SearchSuggestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val searchTerm: String,
)
