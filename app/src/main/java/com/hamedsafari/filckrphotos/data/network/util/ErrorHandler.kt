package com.hamedsafari.filckrphotos.data.network.util

import com.hamedsafari.filckrphotos.utils.NetworkError
import java.net.UnknownHostException

/**
 * An interface to represent a ErrorHandler that returns a NetworkError.
 */
interface ErrorHandler {
    fun handle(exception: Throwable): NetworkError
}

internal class ErrorHandlerImpl : ErrorHandler {
    override fun handle(exception: Throwable): NetworkError {
        return when (exception) {
            is UnknownHostException -> {
                NetworkError("Something went Wrong!\n Check your Network Connection")
            }
            /*
            *   .
            *   .
            *      other Network Exception and Network status's can be handled here
            * */
            else -> {
                NetworkError("Something went Wrong!", exception)
            }
        }
    }
}
