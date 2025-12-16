package dev.aravindraj.composerecipeapp.di.modules


import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.aravindraj.composerecipeapp.BuildConfig
import dev.aravindraj.composerecipeapp.data.repository.RecipeRepository
import dev.aravindraj.composerecipeapp.data.source.local.LocalDataSource
import dev.aravindraj.composerecipeapp.data.source.remote.RecipeAPIService
import dev.aravindraj.composerecipeapp.data.source.remote.RemoteDataSource
import dev.aravindraj.composerecipeapp.di.ApiKey
import dev.aravindraj.composerecipeapp.di.BaseUrl
import dev.aravindraj.composerecipeapp.utils.AppConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = "https://api.spoonacular.com/"

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(): String {
        return AppConstants.getAPIKey()
    }

    @Provides
    @Singleton
    fun provideAssetManager(
        @ApplicationContext context: Context
    ): AssetManager = context.assets

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@ApiKey apiKey: String): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder().addHeader("x-api-key", apiKey).build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor, apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(apiKeyInterceptor).apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
            }
        }.connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrl baseUrl: String, okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideRecipeApiService(retrofit: Retrofit): RecipeAPIService {
        return retrofit.create(RecipeAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: RecipeAPIService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(assetManager: AssetManager, gson: Gson): LocalDataSource {
        return LocalDataSource(assetManager, gson)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(
        remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource
    ): RecipeRepository {
        return RecipeRepository(remoteDataSource, localDataSource)
    }


}