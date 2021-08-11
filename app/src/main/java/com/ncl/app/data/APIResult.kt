package com.ncl.app.data

data class APIResult(
    val apiResponse: APIResponse? = null,
    val error: Int? = null,
    val message: String? = null
)