package com.ncl.app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ncl.app.data.APIResult
import com.ncl.app.repository.MyRepository
import com.ncl.app.R
import com.ncl.app.data.Result
import com.ncl.app.data.TYPE
import kotlinx.coroutines.launch

class MyViewModel(private val myRepository: MyRepository) : ViewModel() {

    private val _apiData = MutableLiveData<APIResult>()
    val apiData: LiveData<APIResult> = _apiData

    fun getApiData(type: TYPE) {
        viewModelScope.launch {
            var result = myRepository.getApiData(type)
            when (result) {
                is Result.Success -> {
                    Log.d("apicalling", "View Model Success with: " + result.data)
                    _apiData.value = APIResult(
                        apiResponse = result.data
                    )
                }
                is Result.Error -> {
                    Log.d("apicalling", "View Model Error..")
                    _apiData.value = APIResult(
                        message = result?.exception?.message,
                        error = R.string.no_internet
                    )
                }
                else -> {
                }
            }
        }
    }
}