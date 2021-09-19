package ir.hajhosseinico.foodandcities.ui.foodandcitylist

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.FoodAndCityListNetworkEntity
import ir.hajhosseinico.foodandcities.repository.FoodAndCityListRepository
import ir.hajhosseinico.foodandcities.model.retrofit.responsemodels.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodAndCityViewModel
@Inject
constructor(
    private val foodAndCityListRepository: FoodAndCityListRepository,
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<FoodAndCityListNetworkEntity>> =
        MutableLiveData()

    val dataState: LiveData<DataState<FoodAndCityListNetworkEntity>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: FoodAndCityListStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is FoodAndCityListStateEvent.GetFoodAndCity -> {
                    // getting data from repository and passing it to fragment
                    foodAndCityListRepository.getFoodAndCities()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is FoodAndCityListStateEvent.None -> {
                    // Do Nothing
                }
            }
        }
    }
}

sealed class FoodAndCityListStateEvent {
    object GetFoodAndCity : FoodAndCityListStateEvent()
    object None : FoodAndCityListStateEvent()
}

