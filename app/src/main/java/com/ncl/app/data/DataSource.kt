package com.ncl.app.data

import ApiService
import com.ncl.app.network.RestApiService
import com.ncl.app.network.ServiceBuilder

class DataSource {

    private val apiService = ServiceBuilder.buildService(ApiService::class.java)

    suspend fun getApiData(type: TYPE): Result<APIResponse> {
        return when (type) {
            TYPE.SKY -> {
                return RestApiService().safeApiCall(call = {
                    apiService.getSKY()
                })
            }
            TYPE.BLISS -> {
                return RestApiService().safeApiCall(call = {
                    apiService.getBLISS()
                })
            }
            else -> {
                return RestApiService().safeApiCall(call = {
                    apiService.getESCAPE()
                })
            }
        }
    }
}