package com.thecat.app.base

import com.thecat.app.base.Status.*

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String?): Resource<T> {
            return Resource(ERROR, null, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(LOADING, null, null)
        }
    }
}
