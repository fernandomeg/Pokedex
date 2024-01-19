package com.gallardf.pokedex.data.repository

import com.gallardf.pokedex.data.Resource

abstract class Repository {

    protected suspend fun <R> networkCall(
        callFunction: suspend () -> R
    ) : Resource<R> {
        return try {
            val response= callFunction.invoke()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}