package br.com.gtbulla.features.repository_git.data.repository

import androidx.paging.PagingData
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitItem
import kotlinx.coroutines.flow.Flow

internal interface RepoGitRepository {

    fun getList(): Flow<PagingData<RepositoryGitItem>>
}