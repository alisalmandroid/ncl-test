package com.ncl.app.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ncl.app.factory.MyViewModelFactory
import com.ncl.app.R
import com.ncl.app.data.TYPE
import com.ncl.app.base.BaseActivity
import com.ncl.app.databinding.ActivityMainBinding
import com.ncl.app.data.APIResult
import com.ncl.app.viewmodel.MyViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MyViewModel>() {

    override fun getLayout() = R.layout.activity_main;

    override fun getViewModel() = MyViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, MyViewModelFactory(this))
            .get(MyViewModel::class.java)

        viewModel.apiData.observe(this, {
            try {
                setTexts(it)
            } catch (e: Exception) {
                setLoadingTexts("exception..")
            }
        })

    }

    private fun setTexts(result: APIResult) {
        result.apiResponse?.shipname?.let { binding.shipName.text = "shipName: $it" }
        result.apiResponse?.shipfacts?.passengercapacity?.let {
            binding.shipFactsPassengerCapacity.text = "passengerCapacity: $it"
        }
        result.apiResponse?.shipfacts?.crew?.let {
            binding.shipFactsCrew.text = "crew: $it"
        }
        result.apiResponse?.shipfacts?.inauguraldate?.let {
            binding.shipFactsInauguralDate.text = "inauguraldate: $it"
        }
    }

    private fun setLoadingTexts(msg: String) {
        binding.shipName.text = msg
        binding.shipFactsPassengerCapacity.text = ""
        binding.shipFactsCrew.text = ""
        binding.shipFactsInauguralDate.text = ""
    }

    fun skyClick(view: View?) {
        setLoadingTexts("Loading..")
        viewModel.getApiData(TYPE.SKY)
    }

    fun blissClick(view: View?) {
        setLoadingTexts("Loading..")
        viewModel.getApiData(TYPE.BLISS)
    }

    fun escapeClick(view: View?) {
        setLoadingTexts("Loading..")
        viewModel.getApiData(TYPE.ESCAPE)
    }

}