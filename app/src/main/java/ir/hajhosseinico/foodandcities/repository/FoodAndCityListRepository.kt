package ir.hajhosseinico.foodandcities.repository

import ir.hajhosseinico.foodandcities.model.retrofit.FoodAndCityRetrofitInterface
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.DataState
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.FoodAndCityListNetworkEntity
import ir.hajhosseinico.foodandcities.util.InternetStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FoodAndCityListRepository
constructor(
    private val foodAndCityRetrofitInterface: FoodAndCityRetrofitInterface,
    private val internetStatus: InternetStatus,
) {
    suspend fun getFoodAndCities(
    ): Flow<DataState<FoodAndCityListNetworkEntity>> =
        flow {
            emit(DataState.Loading)

            // checking internet availability
            if (internetStatus.isInternetAvailable()) {
                try {
                    // getting data from server
                    val baseFoodAndCity = foodAndCityRetrofitInterface.getFoodAndCityList()
                    emit(DataState.Success(baseFoodAndCity))
                } catch (e: Exception) {
                    emit(DataState.Error(e))
                }
            } else {
                emit(DataState.Error(java.lang.Exception("Internet is not available")))
            }
        }
}
