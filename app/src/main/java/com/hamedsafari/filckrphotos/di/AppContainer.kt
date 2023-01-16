package com.hamedsafari.filckrphotos.di

import android.content.Context
import com.hamedsafari.filckrphotos.data.local.FilckrDataBase
import com.hamedsafari.filckrphotos.data.local.dao.BookmarkDao
import com.hamedsafari.filckrphotos.data.local.dao.SearchSuggestionDao
import com.hamedsafari.filckrphotos.data.network.dataSource.PhotosDataSource
import com.hamedsafari.filckrphotos.data.network.dataSource.PhotosNetworkDataSource
import com.hamedsafari.filckrphotos.data.network.service.PhotoService
import com.hamedsafari.filckrphotos.data.network.util.ErrorHandler
import com.hamedsafari.filckrphotos.data.network.util.ErrorHandlerImpl
import com.hamedsafari.filckrphotos.data.repository.BookmarkRepository
import com.hamedsafari.filckrphotos.data.repository.BookmarkRepositoryImpl
import com.hamedsafari.filckrphotos.data.repository.PhotosRepository
import com.hamedsafari.filckrphotos.data.repository.PhotosRepositoryImpl
import com.hamedsafari.filckrphotos.features.bookmark.BookmarkViewModelFactory
import com.hamedsafari.filckrphotos.features.detail.DetailViewModelFactory
import com.hamedsafari.filckrphotos.features.search.SearchViewModelFactory

/**
 * A Container for the Dependency Injection.
 * We can use Library's like Dagger, hilt, koin , ...
 * According to the scope of the project I prefer to do the manual way of DI
 * And don't use a library. ¯\_(ツ)_/¯
 */
class AppContainer(context: Context) {
    //Service
    private val service: PhotoService by lazy { PhotoService.create() }

    //Error Handler
    private val errorHandler: ErrorHandler by lazy { ErrorHandlerImpl() }

    //dataBase
    private val dataBase : FilckrDataBase by lazy { FilckrDataBase.buildDatabase(context) }
    private val searchDao: SearchSuggestionDao by lazy { dataBase.searchDao() }
    private val bookmarkedDao: BookmarkDao by lazy { dataBase.bookmarkedDao() }

    //Datasource
    private val dataSource: PhotosDataSource by lazy { PhotosNetworkDataSource(service, errorHandler) }

    //Repository
    private val photosRepository: PhotosRepository by lazy { PhotosRepositoryImpl(searchDao, dataSource) }
    private val bookmarkRepository: BookmarkRepository by lazy { BookmarkRepositoryImpl(bookmarkedDao) }

    //Presentation - viewModelFactory
    val searchViewModelFactory by lazy { SearchViewModelFactory(photosRepository) }
    fun detailViewModelFactory(id: String) = DetailViewModelFactory(id, bookmarkRepository)
    val bookmarkViewModelFactory by lazy { BookmarkViewModelFactory(bookmarkRepository) }
}
