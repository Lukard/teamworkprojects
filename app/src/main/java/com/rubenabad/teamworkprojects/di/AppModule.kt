package com.rubenabad.teamworkprojects.di

import com.rubenabad.teamworkprojects.api.ProjectsApi
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

    bean { ProjectsRepositoryImpl(get()) as ProjectsRepository }

    bean { createWebService<ProjectsApi>(teamworkURL, apiKey) }

}

const val teamworkURL = "https://yat.teamwork.com/"
const val apiKey = "twp_TEbBXGCnvl2HfvXWfkLUlzx92e3T"

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