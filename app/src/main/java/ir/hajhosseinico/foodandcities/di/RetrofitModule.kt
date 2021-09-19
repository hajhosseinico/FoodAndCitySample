package ir.hajhosseinico.foodandcities.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.hajhosseinico.foodandcities.model.retrofit.FoodAndCityRetrofitInterface
import ir.hajhosseinico.foodandcities.util.InternetStatus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder().baseUrl("https://api.npoint.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): FoodAndCityRetrofitInterface {
        return retrofit
            .build()
            .create(FoodAndCityRetrofitInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideInternetStatus(@ApplicationContext context: Context): InternetStatus {
        return InternetStatus(context)
    }
}