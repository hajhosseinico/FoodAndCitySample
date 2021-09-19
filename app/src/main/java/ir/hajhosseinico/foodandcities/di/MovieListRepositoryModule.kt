package ir.hajhosseinico.foodandcities.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hajhosseinico.foodandcities.model.retrofit.FoodAndCityRetrofitInterface
import ir.hajhosseinico.foodandcities.repository.FoodAndCityListRepository
import ir.hajhosseinico.foodandcities.util.InternetStatus
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MovieListRepositoryModule {
    @Singleton
    @Provides
    fun provideMovieListRepository(
        retrofitInterface: FoodAndCityRetrofitInterface,
        internetStatus: InternetStatus
    ): FoodAndCityListRepository {
        return FoodAndCityListRepository(retrofitInterface,internetStatus)
    }
}