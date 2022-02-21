package br.com.gtbulla.features.repository_git.data.service

import br.com.gtbulla.libraries.common.model.data.RepositoryGitItemResponse
import br.com.gtbulla.libraries.data.response.ResponseItems
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories")
    suspend fun getRepositoryList(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
    ): ResponseItems<RepositoryGitItemResponse>
}