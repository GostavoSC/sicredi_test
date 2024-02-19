package gstv.sicredi.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun interface Mapper<I, O> {

    fun map(from: I): O
}

fun <I, O> I.mapWith(mapper: Mapper<I, O>) = mapper.map(this)

fun <I, O> List<I>.mapWith(mapper: Mapper<I, O>) = map { it.mapWith(mapper) }

fun <I : Any, O : Any> Flow<List<I>>.mapFlowWith(mapper: Mapper<I, O>) = map { it.mapWith(mapper) }
