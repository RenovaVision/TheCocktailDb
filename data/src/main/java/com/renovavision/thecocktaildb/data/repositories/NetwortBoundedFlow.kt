package com.renovavision.thecocktaildb.data.repositories

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal fun <RESULT, REQUEST> networkBoundedFlow(
    local: Flow<RESULT>,
    save: suspend (response: REQUEST) -> Unit,
    remote: suspend () -> REQUEST,
    log: (e: Throwable) -> Unit = { Log.d("networkBoundedFlow:", it.message) }
): Flow<RESULT> = flow {
    try {
        local.first()?.let { emit(it) }
        remote()?.let { save(it) }
    } catch (e: Throwable) {
        log(e)
    }
    emitAll(local.filter { it != null }.map { it as RESULT })
}