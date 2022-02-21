package br.com.gtbulla.libraries.common.model.data

import com.squareup.moshi.Json

data class RepositoryGitOwnerResponse(
    val login: String,
    @field:Json(name = "avatar_url") val avatarUrl: String,
)
