package br.com.gtbulla.features.repository_git.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.gtbulla.features.repository_git.data.service.GithubService
import br.com.gtbulla.libraries.common.model.data.RepositoryGitItemResponse
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

internal class RepositoryPagingSource(
    private val service: GithubService
) : PagingSource<Int, RepositoryGitItemResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryGitItemResponse> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            Log.e("PagingSource", "$pageIndex")
            val response = service.getRepositoryList(
                "language:kotlin",
                "stars",
                pageIndex,
            )
            val nextKey = if (response.items.isEmpty()) null else pageIndex + 1
            LoadResult.Page(
                data = response.items,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            Log.e("PagingSourceIOExc", "$pageIndex")
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e("PagingSourceHttpExc", "$pageIndex")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepositoryGitItemResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}