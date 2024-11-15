package com.tmdbtestapp.common.base

import com.tmdbtestapp.common.utils.DataState
import com.tmdbtestapp.common.utils.ErrorModel
import retrofit2.Response


abstract class BaseRepository {
    fun <I, O> obtain(
        response: Response<I?>,
        mapper: Mapper<I, O?>,
    ): DataState<O> {
        return try {
            if (!response.isSuccessful) {
                return DataState.error(ErrorModel.create("Request failed"))
            }

            val body = response.body() ?: return DataState.error(ErrorModel.EmptyBody)
            val mappedResponseBody = mapper(body) ?: return DataState.error(ErrorModel.EmptyBody)

            DataState.success(mappedResponseBody)
        } catch (e: Exception) {
            DataState.error(ErrorModel.unknown())
        }
    }
}