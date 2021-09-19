package ir.hajhosseinico.foodandcities.model.retrofit.responsemodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class CityNetworkEntity (
    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("image")
    @Expose
    var image: String,


    @SerializedName("description")
    @Expose
    var description: String,
): Serializable