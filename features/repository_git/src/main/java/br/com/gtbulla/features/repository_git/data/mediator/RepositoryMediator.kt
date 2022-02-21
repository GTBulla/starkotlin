package br.com.gtbulla.features.repository_git.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import br.com.gtbulla.features.repository_git.data.service.GithubService
import br.com.gtbulla.libraries.common.model.entity.RepositoryGitItemEntity
import br.com.gtbulla.libraries.common.model.mapper.RepositoryGitMapper
import br.com.gtbulla.libraries.data.RepositoryDatabase
import br.com.gtbulla.libraries.data.entity.RepositoryGitPageEntity
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
internal class RepositoryMediator(
    private val mapper: RepositoryGitMapper,
    private val service: GithubService,
    private val database: RepositoryDatabase,
) : RemoteMediator<Int, RepositoryGitItemEntity>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryGitItemEntity>
    ): MediatorResult {
        var pageIndex = 0
        return try {
            val repositoryPage = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    getPage()
                }
            }
            pageIndex = repositoryPage?.let { it.page + 1 } ?: STARTING_PAGE_INDEX
            val response = service.getRepositoryList(
                "language:kotlin",
                "stars",
                pageIndex
            )
            val list = response.items.map { mapper.mapRemoteToEntity(it) }
            list?.let {
                database.withTransaction {
                    database.repositoryGitPageDao.save(RepositoryGitPageEntity(pageIndex))
                    database.repositoryGitItemDao.save(list)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.items.isEmpty())
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getPage(): RepositoryGitPageEntity? {
        return database.repositoryGitPageDao.getPage()
    }
}
