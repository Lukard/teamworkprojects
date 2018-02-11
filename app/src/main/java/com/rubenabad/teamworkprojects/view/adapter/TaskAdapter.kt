package com.rubenabad.teamworkprojects.view.adapter

import android.view.ViewGroup
import com.rubenabad.teamworkprojects.data.Task
import com.rubenabad.teamworkprojects.view.item.TaskItemView


class TaskAdapter : RecyclerViewAdapterBase<Task, TaskItemView>() {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): TaskItemView =
            TaskItemView(parent.context)

    override fun onBindViewHolder(holder: ViewWrapper<TaskItemView>?, position: Int) {
        holder?.view?.bind(items[position])
    }

}