package br.com.gtbulla.libraries.common.model.data

import com.squareup.moshi.Json

data class RepositoryGitItemResponse(
    val id: Long,
    val name: String,
    val owner: RepositoryGitOwnerResponse,
    @field:Json(name = "stargazers_count") val starCount: Int,
    @field:Json(name = "forks_count") val forkCount: Int,
)
