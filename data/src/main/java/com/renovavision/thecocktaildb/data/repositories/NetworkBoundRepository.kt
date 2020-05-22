package com.renovavision.thecocktaildb.data.repositories

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<RESULT, REQUEST> {

    fun asFlow() = flow<RESULT> {

        try {
            emit(fetchFromLocal().first())

            val apiResponse = fetchFromRemote()

            if (apiResponse != null) {
                saveRemoteData(apiResponse)
            }
        } catch (e: Exception) {
            Log.d(NetworkBoundRepository::class.java.simpleName, e.message)
        }

        emitAll(fetchFromLocal().map { it })
    }

    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)

    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>

    @MainThread
    protected abstract suspend fun fetchFromRemote(): REQUEST
}