package com.hamedsafari.filckrphotos.data.repository

import com.hamedsafari.filckrphotos.data.local.dao.SearchSuggestionDao
import com.hamedsafari.filckrphotos.data.local.model.SearchSuggestionEntity
import com.hamedsafari.filckrphotos.data.network.dataSource.PhotosDataSource
import com.hamedsafari.filckrphotos.domain.Photo
import com.hamedsafari.filckrphotos.utils.Result
import com.hamedsafari.filckrphotos.data.repository.mappers.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * An interface to represent a repository that returns Photos,
 * and save user search terms.
 */
interface PhotosRepository {
    /**
     * Returns Photos.
     *
     * Assumptions:
     *
     *  - List of Photo returned based on search terms.
     */
    suspend fun search(searchTerms: String): Result<List<Photo>>
    /**
     * Assumptions:
     *  - Save Users search terms to DB.
     */
    suspend fun saveSearchTerm(searchTerms: String)
    /**
     * Returns Flow<List[String]>.
     *
     * Assumptions:
     *  - List of saved search terms returned.
     */
    suspend fun getSearchSuggestions(): Flow<List<String>>
}

/**
 *  An implementation for the PhotosRepository which search's Photos from dataSource,
 *  and save users search terms in DB.
 */
internal class PhotosRepositoryImpl(
    private val searchSuggestionDao: SearchSuggestionDao,
    private val dataSource: PhotosDataSource
) : PhotosRepository {

    override suspend fun search(searchTerms: String): Result<List<Photo>> =
        dataSource.searchPhotos(searchTerms).mapOnSuccess {
            it.toDomainModel()
        }

    override suspend fun saveSearchTerm(searchTerms: String) {
        withContext(Dispatchers.IO) {
            searchSuggestionDao.insertSearchTerms(SearchSuggestionEntity(searchTerm = searchTerms))
        }
    }

    override suspend fun getSearchSuggestions(): Flow<List<String>> =
        searchSuggestionDao.loadAllSearchTerms().map { it.map { it.searchTerm } }

}
