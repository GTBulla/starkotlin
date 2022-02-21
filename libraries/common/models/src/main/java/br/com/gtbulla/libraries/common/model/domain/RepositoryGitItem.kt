package br.com.gtbulla.libraries.common.model.domain

data class RepositoryGitItem(
    val id: Long,
    val name: String,
    val owner: RepositoryGitOwner,
    val starCount: Int,
    val forkCount: Int,
)