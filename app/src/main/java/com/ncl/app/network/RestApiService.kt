package com.ncl.app.network

import android.util.Log
import com.ncl.app.data.Result
import retrofit2.Response

class RestApiService {

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val myResp = call.invoke()
//            Log.d("apicalling", "RestApiService myResp: " + myResp.body()!!)
            when {
                myResp.isSuccessful -> {
                    Result.Success(myResp.body()!!)
                }
                myResp.code() == 434 -> {// user is banned
                    Result.Error(
                        Exception(myResp.errorBody()?.string() ?: "User is banned"),
                        myResp.code()
                    )
                }
                myResp.code() == 401 -> { // unauthenticated
                    Result.Error(
                        Exception(myResp.errorBody()?.string() ?: "User is unauthenticated"),
                        myResp.code()
                    )
                }
                myResp.code() == 433 -> { // Verification Pending
                    Result.Error(
                        Exception(myResp.errorBody()?.string() ?: "Verification Pending"),
                        myResp.code()
                    )
                }
                else -> {
                    Log.d("apicalling", "RestApiService ErrorCode: " + myResp.code())
                    Log.d("apicalling", "RestApiService Error Message: " + myResp.message())
                    Log.d(
                        "apicalling",
                        "RestApiService Error body: " + myResp.errorBody().toString()
                    )
                    Result.Error(
                        Exception(myResp.errorBody()?.string() ?: "Something goes wrong"),
                        myResp.code()
                    )
                }
            }

        } catch (e: Exception) {
            Log.d("apicalling", "RestApiService Exception: " + e.message)
            Result.Error(Exception(e.message ?: "Internet error runs"), 500)
        }
    }

}