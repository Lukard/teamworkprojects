package com.rubenabad.teamworkprojects.data

data class Company(val name: String)

data class Project(val name: String, val description: String, val company: Company, val logo: String)

data class ProjectsResponse(val STATUS: String, val projects: List<Project>)