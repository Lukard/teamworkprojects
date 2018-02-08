package com.rubenabad.teamworkprojects

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.rubenabad.teamworkprojects.api.ProjectsApi
import com.rubenabad.teamworkprojects.repository.ProjectsRepositoryImpl
import com.rubenabad.teamworkprojects.utils.Base64Coder
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiKey = "twp_TEbBXGCnvl2HfvXWfkLUlzx92e3T"
        val password = "yatyatyat27"
        val teamworkURL = "https://yat.teamwork.com/"
        val authorization = "BASIC ${Base64Coder.encodeString("$apiKey:$password")}"

        val client = OkHttpClient.Builder()
                .addInterceptor {
                    it.proceed(
                            it.request().newBuilder()
                                    .addHeader("Authorization", authorization)
                                    .build()
                    )
                }.build()

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(teamworkURL)
                .client(client)
                .build()

        val repository = ProjectsRepositoryImpl(retrofit.create(ProjectsApi::class.java))

        repository
                .getProjects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { projectList ->
                    Log.d("TAG", projectList.joinToString { it.logo })
                }

        Single
                .just(listOf("1", "2", "3"))
                .mergeWith(
                        Single.just(listOf("4", "5", "6"))
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("TAG", it.joinToString())
                }
    }
}
