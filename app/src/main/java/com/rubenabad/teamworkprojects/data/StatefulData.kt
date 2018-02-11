package com.rubenabad.teamworkprojects.data

data class StatefulData<out T> (
        val state: State = State.LOADING,
        val data: T? = null,
        val error: Throwable? = null
)

enum class State {
    LOADING, SUCCESS, ERROR
}