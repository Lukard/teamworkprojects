package com.rubenabad.teamworkprojects.data

data class Tag(val name: String?, val color: String?)

data class Company(val name: String?)

data class Project(val name: String?, val description: String?, val company: Company?, val logo: String?,
                   val tags: List<Tag>?)

data class ProjectsResponse(val STATUS: String?, val projects: List<Project>?)

data class StatefulData<out T> (
        val state: State = State.START_LOADING,
        val data: T? = null,
        val error: Throwable? = null
)

enum class State {
    START_LOADING, END_LOADING, SUCCESS, ERROR
}