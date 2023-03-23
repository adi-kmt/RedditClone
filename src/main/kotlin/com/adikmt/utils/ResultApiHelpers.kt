package com.adikmt.utils

/**
 * Got from [ProAndroidDev Article] {https://proandroiddev.com/resilient-use-cases-with-kotlin-result-coroutines-and-annotations-511df10e2e16}
 */

import com.adikmt.dtos.SerializedException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.CancellationException

/**
 * Like [runCatching], but with proper coroutines cancellation handling. Also only catches [Exception] instead of [Throwable].
 *
 * Cancellation exceptions need to be rethrown. See https://github.com/Kotlin/kotlinx.coroutines/issues/1814.
 */
inline fun <R> resultOf(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}

/**
 * Like [runCatching], but with proper coroutines cancellation handling. Also only catches [Exception] instead of [Throwable].
 *
 * Cancellation exceptions need to be rethrown. See https://github.com/Kotlin/kotlinx.coroutines/issues/1814.
 */
inline fun <T, R> T.resultOf(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}

/**
 * Like [mapCatching], but uses [resultOf] instead of [runCatching].
 */
inline fun <R, T> Result<T>.mapResult(transform: (value: T) -> R, transformFail: (failure: Throwable) -> R): Result<R> {
    val successResult = getOrNull()
    val failureResult = exceptionOrNull() ?: error("Unreachable state")
    return when {
        successResult != null -> resultOf { transform(successResult) }
        else -> resultOf { transformFail(failureResult) }
    }
}

/**
 * Helps in deconstructing result
 */

suspend inline fun <reified T> deconstructResult(
    pipeline: PipelineContext<Unit, ApplicationCall>,
    result: Result<T>,
    httpStatusCode: HttpStatusCode
) {
    result.mapResult({ value: T ->
        pipeline.call.respond(httpStatusCode, value, typeInfo<T>())
    }, {
        pipeline.call.respond(HttpStatusCode.BadRequest, SerializedException(it.message))
    })
}

