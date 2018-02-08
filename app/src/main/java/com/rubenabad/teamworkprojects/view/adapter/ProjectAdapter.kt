package com.rubenabad.teamworkprojects.view.adapter

import android.view.ViewGroup
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.view.item.ProjectItemView

class ProjectAdapter : RecyclerViewAdapterBase<Project, ProjectItemView>() {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): ProjectItemView =
            ProjectItemView(parent.context)

    override fun onBindViewHolder(holder: ViewWrapper<ProjectItemView>, position: Int) {
        holder.view.bind(items[position])
    }
}