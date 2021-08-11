package com.ncl.app.repository

import com.ncl.app.data.DataSource
import com.ncl.app.data.APIResponse
import com.ncl.app.data.Result
import com.ncl.app.data.TYPE

class MyRepository constructor(
    val dataSource: DataSource
) {
    suspend fun getApiData(type: TYPE): Result<APIResponse> {
        return dataSource.getApiData(type)
    }
}