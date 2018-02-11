package com.rubenabad.teamworkprojects.api

import com.google.gson.annotations.SerializedName
import com.rubenabad.teamworkprojects.data.Task

data class TaskResponse(val STATUS: String?, @SerializedName("todo-items") val tasks: List<Task>?)