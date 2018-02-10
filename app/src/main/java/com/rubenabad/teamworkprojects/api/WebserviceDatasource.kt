package com.rubenabad.teamworkprojects.api

import io.reactivex.Single
import retrofit2.http.GET

interface WebserviceDatasource {

    @GET("projects.json")
    fun getProjects(): Single<ProjectsResponse>

}