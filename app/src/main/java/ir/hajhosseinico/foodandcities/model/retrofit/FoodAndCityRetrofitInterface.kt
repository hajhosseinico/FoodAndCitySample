package ir.hajhosseinico.foodandcities.model.retrofit

import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.FoodAndCityListNetworkEntity
import retrofit2.http.GET

/**
 * Api queries
 * used by retrofit
 */
interface FoodAndCityRetrofitInterface {
    @GET("a2b63ef226c08553b2f9")
    suspend fun getFoodAndCityList(): FoodAndCityListNetworkEntity
}