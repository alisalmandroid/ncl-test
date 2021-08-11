package com.ncl.app.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ncl.app.data.DataSource
import com.ncl.app.repository.MyRepository
import com.ncl.app.viewmodel.MyViewModel

/**
 * ViewModel provider factory to instantiate MyViewModel.
 * Required given MembersViewModel has a non-empty constructor
 */
class MyViewModelFactory(
    val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(
                myRepository = MyRepository(
                    dataSource = DataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}