package com.rubenabad.teamworkprojects.data

data class Project(val name: String, val logo: String)

data class ProjectsResponse(val STATUS: String, val projects: List<Project>)