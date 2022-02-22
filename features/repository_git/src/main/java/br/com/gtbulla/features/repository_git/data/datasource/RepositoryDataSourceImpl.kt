package br.com.gtbulla.features.repository_git.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.gtbulla.features.repository_git.data.mediator.PageDataStore
import br.com.gtbulla.features.repository_git.data.mediator.RepositoryMediator
import br.com.gtbulla.features.repository_git.data.service.GithubService
import br.com.gtbulla.libraries.common.model.entity.RepositoryGitItemEntity
import br.com.gtbulla.libraries.common.model.mapper.RepositoryGitMapper
import br.com.gtbulla.libraries.data.RepositoryDatabase
import kotlinx.coroutines.flow.Flow

const val PAGE_SIZE = 30

internal class RepositoryDataSourceImpl(
    private val pageDataStore: PageDataStore,
    private val mapper: RepositoryGitMapper,
    private val service: GithubService,
    private val database: RepositoryDatabase,
) : RepositoryDataSource {

    @OptIn(ExperimentalPagingApi::class)
    override fun getList(): Flow<PagingData<RepositoryGitItemEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            remoteMediator = RepositoryMediator(pageDataStore, mapper, service, database),
            pagingSourceFactory = { database.repositoryGitItemDao.getList() },
        ).flow
    }
}