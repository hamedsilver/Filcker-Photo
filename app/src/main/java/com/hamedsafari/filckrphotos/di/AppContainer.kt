package com.hamedsafari.filckrphotos.di

import android.content.Context
import com.hamedsafari.filckrphotos.data.local.FilckrDataBase
import com.hamedsafari.filckrphotos.data.local.dao.SearchSuggestionDao
import com.hamedsafari.filckrphotos.data.network.dataSource.PhotosDataSource
import com.hamedsafari.filckrphotos.data.network.dataSource.PhotosNetworkDataSource
import com.hamedsafari.filckrphotos.data.network.service.PhotoService
import com.hamedsafari.filckrphotos.data.network.util.ErrorHandler
import com.hamedsafari.filckrphotos.data.network.util.ErrorHandlerImpl
import com.hamedsafari.filckrphotos.data.repository.PhotosRepository
import com.hamedsafari.filckrphotos.data.repository.PhotosRepositoryImpl
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

    //Datasource
    private val dataSource: PhotosDataSource by lazy { PhotosNetworkDataSource(service, errorHandler) }

    //Repository
    private val repository: PhotosRepository by lazy { PhotosRepositoryImpl(dataBase.searchDao(), dataSource) }

    //Presentation - viewModelFactory
    val filmDetailViewModelFactory by lazy { SearchViewModelFactory(repository) }

}