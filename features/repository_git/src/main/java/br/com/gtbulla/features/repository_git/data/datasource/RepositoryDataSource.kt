package br.com.gtbulla.features.repository_git.data.datasource

import androidx.paging.PagingData
import br.com.gtbulla.libraries.common.model.entity.RepositoryGitItemEntity
import kotlinx.coroutines.flow.Flow

internal interface RepositoryDataSource {

    fun getList(): Flow<PagingData<RepositoryGitItemEntity>>
}