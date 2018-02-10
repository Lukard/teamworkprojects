package com.rubenabad.teamworkprojects.di

import com.rubenabad.teamworkprojects.BuildConfig
import com.rubenabad.teamworkprojects.api.WebserviceDatasource
import com.rubenabad.teamworkprojects.db.DatabaseDataSource
import com.rubenabad.teamworkprojects.repository.ProjectsRepository
import com.rubenabad.teamworkprojects.repository.ProjectsRepositoryImpl
import com.rubenabad.teamworkprojects.utils.Base64Coder
import com.rubenabad.teamworkprojects.viewmodel.ProjectListViewModel
import okhttp3.OkHttpClient
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = applicationContext {

    viewModel { ProjectListViewModel(get()) }

    bean { ProjectsRepositoryImpl(get(), get()) as ProjectsRepository }

    bean { createWebService<WebserviceDatasource>(teamworkURL, apiKey) }

    bean { DatabaseDataSource.build(get()) }

}

const val teamworkURL = "https://yat.teamwork.com/"
val apiKey: String = BuildConfig.YakApiKey

fun getAuthorization(apiKey: String) = "BASIC ${Base64Coder.encodeString("$apiKey:xxx")}"

fun createClient(apiKey: String): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            it.proceed(
                    it.request().newBuilder()
                            .addHeader("Authorization", getAuthorization(apiKey))
                            .build()
            )
        }.build()


inline fun <reified T> createWebService(url: String, apiKey: String): T =
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .client(createClient(apiKey))
                .build()
                .create(T::class.java)
