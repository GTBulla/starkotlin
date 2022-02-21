package br.com.gtbulla.features.repository_git.data

import br.com.gtbulla.libraries.common.model.data.RepositoryGitItemResponse
import br.com.gtbulla.libraries.data.response.ResponseItems
import br.com.gtbulla.libraries.testcore.helper.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

object RepositoryData {

    private val moshi = Moshi.Builder().build()
    private val moviesResponseGenericType: Type = Types.newParameterizedType(
        ResponseItems::class.java,
        RepositoryGitItemResponse::class.java
    )
    private val remoteMoviesAdapter: JsonAdapter<ResponseItems<RepositoryGitItemResponse>> =
        moshi.adapter(moviesResponseGenericType)

    fun provideRemoteFromAssets(): List<RepositoryGitItemResponse> {
        return remoteMoviesAdapter.fromJson(
            FileHelper.readFromResources(
                fileName = "repositories.json"
            )
        )?.items ?: listOf()
    }
}