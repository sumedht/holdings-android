package com.sumedh.demo.data.repository

import android.util.Log
import com.google.gson.JsonSyntaxException
import com.sumedh.demo.common.Resource
import com.sumedh.demo.data.local.HoldingsDao
import com.sumedh.demo.data.local.entity.toHolding
import com.sumedh.demo.data.remote.HoldingsApi
import com.sumedh.demo.data.remote.dto.toEntity
import com.sumedh.demo.data.remote.dto.toHolding
import com.sumedh.demo.domain.error.AppError
import com.sumedh.demo.domain.model.Holding
import com.sumedh.demo.domain.repository.HoldingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HoldingsRepositoryImpl @Inject constructor(
    private val api: HoldingsApi,
    private val dao: HoldingsDao
): HoldingsRepository {

    override fun getHoldings(): Flow<Resource<List<Holding>>> = flow {
        dao.observeHoldings().collect { holdings ->
            if (holdings.isEmpty()) {
                emit(Resource.Error(AppError.EmptyCache))
            } else {
                emit(Resource.Success(holdings.map { it.toHolding() }))
            }
        }
    }

    override fun refreshHoldings(): Flow<Resource<List<Holding>>> = flow{
        try {
            val remoteHoldings = api.getHoldings().data.userHolding
            dao.insertAll(remoteHoldings.map { it.toEntity() })
            emit(Resource.Success(remoteHoldings.map { it.toHolding() }))
        } catch (e: IOException) {
            emit(Resource.Error(AppError.NetworkUnavailable))
        } catch (e: JsonSyntaxException) {
            emit(Resource.Error(AppError.ParsingError))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    AppError.ApiError(
                        code = e.code(),
                        message = e.message
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(AppError.Unknown(e)))
        }
    }
}