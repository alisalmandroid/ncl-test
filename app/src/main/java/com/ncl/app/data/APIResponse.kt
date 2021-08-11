package com.ncl.app.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class APIResponse(

    @Expose
    @SerializedName("shipName")
    val shipname: String,
    @Expose
    @SerializedName("shipFacts")
    var shipfacts: Shipfacts
)

data class Shipfacts(
    @Expose
    @SerializedName("passengerCapacity")
    val passengercapacity: String,
    @Expose
    @SerializedName("crew")
    val crew: String,
    @Expose
    @SerializedName("inauguralDate")
    val inauguraldate: String
)
