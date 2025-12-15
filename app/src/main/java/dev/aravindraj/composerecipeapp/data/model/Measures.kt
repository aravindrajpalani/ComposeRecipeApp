package dev.aravindraj.composerecipeapp.data.model

import com.google.gson.annotations.SerializedName


data class Measures(

    @SerializedName("us")
    var us: UnitSize? = UnitSize(),
    @SerializedName("metric")
    var metric: Metric? = Metric()

)
