package ir.hajhosseinico.foodandcities.model.retrofit.responsemodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FoodAndCityListNetworkEntity (
    @SerializedName("foods")
    @Expose
    var foods: ArrayList<FoodNetworkEntity>,

    @SerializedName("cities")
    @Expose
    var cities: ArrayList<CityNetworkEntity>,
    ): Serializable