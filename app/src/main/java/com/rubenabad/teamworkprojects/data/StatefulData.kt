package com.rubenabad.teamworkprojects.data

data class StatefulData<out T> (
        val state: State = State.START_LOADING,
        val data: T? = null,
        val error: Throwable? = null
)

enum class State {
    START_LOADING, END_LOADING, SUCCESS, ERROR
}