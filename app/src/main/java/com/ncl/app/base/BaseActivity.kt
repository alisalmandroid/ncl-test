package com.ncl.app.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<VDB : ViewDataBinding, VM : ViewModel
//        ,VMF : ViewModelProvider.Factory
        > : AppCompatActivity() {

    protected lateinit var binding: VDB

    protected lateinit var viewModel: VM

    @LayoutRes
    abstract fun getLayout(): Int

    //    abstract fun getViewModel(): VM
    abstract fun getViewModel(): Class<VM>

//    abstract fun getViewModelFactory(): Class<VMF>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
//        viewModel = getViewModel()
        try {
            viewModel = ViewModelProvider(this).get(getViewModel())
        } catch (e: Exception) {
        }

        binding = DataBindingUtil.setContentView(this, getLayout())
        // dataBinding.lifecycleOwner = this
        binding.executePendingBindings()
    }

}