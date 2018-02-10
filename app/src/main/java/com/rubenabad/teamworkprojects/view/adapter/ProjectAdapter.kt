package com.rubenabad.teamworkprojects.view.adapter

import android.view.ViewGroup
import com.rubenabad.teamworkprojects.data.Project
import com.rubenabad.teamworkprojects.view.item.ProjectItemView

class ProjectAdapter(val projectClick: (Project) -> Unit) : RecyclerViewAdapterBase<Project, ProjectItemView>() {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): ProjectItemView =
            ProjectItemView(parent.context)

    override fun onBindViewHolder(holder: ViewWrapper<ProjectItemView>, position: Int) {
        holder.view.apply {
            bind(items[position])
            setOnClickListener { projectClick(items[position]) }
        }
    }

}