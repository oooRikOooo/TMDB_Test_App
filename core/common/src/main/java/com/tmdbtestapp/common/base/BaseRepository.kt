package com.tmdbtestapp.common.base

import retrofit2.Response

typealias Mapper<Input, Output> = (Input) -> Output

abstract class BaseRepository {
    fun <I, O> obtain(
        response: Response<I?>,
        mapper: Mapper<I, O?>,
    ): Result<O> {
        return try {
            if (!response.isSuccessful) {
                return Result.failure(Exception("Request failed"))
            }

            val body = response.body() ?: return Result.failure(Exception("EmptyBody"))
            val mappedResponseBody = mapper(body) ?: return Result.failure(Exception("EmptyBody"))

            Result.success(mappedResponseBody)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}