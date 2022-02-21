package br.com.gtbulla.libraries.common.model.mapper

import br.com.gtbulla.libraries.common.model.data.RepositoryGitItemResponse
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitItem
import br.com.gtbulla.libraries.common.model.entity.RepositoryGitItemEntity
import br.com.gtbulla.libraries.common.model.presentation.RepositoryGitItemUI

interface RepositoryGitMapper {

    suspend fun mapRemoteListToDomain(remoteList: List<RepositoryGitItemResponse>): List<RepositoryGitItem>

    suspend fun mapEntityToDomain(entity: RepositoryGitItemEntity): RepositoryGitItem

    suspend fun mapRemoteToEntity(remote: RepositoryGitItemResponse): RepositoryGitItemEntity

    suspend fun mapRemoteToDomain(remote: RepositoryGitItemResponse): RepositoryGitItem

    suspend fun mapDomainListToUI(domainList: List<RepositoryGitItem>): List<RepositoryGitItemUI>

    suspend fun mapDomainToUI(domain: RepositoryGitItem): RepositoryGitItemUI

}