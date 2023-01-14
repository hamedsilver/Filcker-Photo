package com.hamedsafari.filckrphotos

import android.app.Application
import com.hamedsafari.filckrphotos.di.AppContainer

/**
 * Application class.
 */
class MainApplication : Application() {
    // Instance of AppContainer that will be used by all the Fragments of the app
    val appContainer = AppContainer()
}