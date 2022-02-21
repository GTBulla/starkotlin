package br.com.gtbulla.features.repository_git.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import br.com.gtbulla.features.repository_git.data.datasource.RepositoryDataSource
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitItem
import br.com.gtbulla.libraries.common.model.mapper.RepositoryGitMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RepoGitRepositoryImpl(
    private val dataSource: RepositoryDataSource,
    private val mapper: RepositoryGitMapper,
) : RepoGitRepository {

    override fun getList(): Flow<PagingData<RepositoryGitItem>> {
        return dataSource.getList()
            .map { pagingData ->
                pagingData.map {
                    mapper.mapEntityToDomain(it)
                }
            }
    }

}