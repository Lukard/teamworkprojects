package com.rubenabad.teamworkprojects.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WebserviceDataSource {

    @GET("projects.json")
    fun getProjects(): Single<ProjectsResponse>

    @GET("projects/{id}/tasks.json")
    fun getTasks(@Path("id") projectId: Long): Single<TaskResponse>

}