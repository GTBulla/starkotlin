package br.com.gtbulla.features.repository_git.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import br.com.gtbulla.libraries.common.model.mapper.RepositoryGitMapper
import br.com.gtbulla.libraries.common.model.presentation.RepositoryGitItemUI
import br.com.gtbulla.features.repository_git.data.repository.RepoGitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RepositoryViewModel(
    private val coroutineScope: CoroutineScope? = null,
    private val repository: RepoGitRepository,
    private val mapper: RepositoryGitMapper,
) : ViewModel() {

    fun getList(): Flow<PagingData<RepositoryGitItemUI>> {
        return repository.getList()
            .map { pagingData ->
                pagingData.map {
                    mapper.mapDomainToUI(it)
                }
            }
            .cachedIn(coroutineScope ?: this.viewModelScope)
    }

}