package br.com.gtbulla.libraries.common.model.mapper

import br.com.gtbulla.libraries.common.model.data.RepositoryGitItemResponse
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitItem
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitOwner
import br.com.gtbulla.libraries.common.model.entity.RepositoryGitItemEntity
import br.com.gtbulla.libraries.common.model.presentation.RepositoryGitItemUI
import br.com.gtbulla.libraries.common.model.presentation.RepositoryGitOwnerUI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RepositoryGitMapperImpl(
    private val defaultDispatcher: CoroutineDispatcher
) : RepositoryGitMapper {

    override suspend fun mapRemoteListToDomain(
        remoteList: List<RepositoryGitItemResponse>
    ): List<RepositoryGitItem> {
        return withContext(defaultDispatcher) {
            remoteList.map {
                mapRemoteToDomain(it)
            }
        }
    }

    override suspend fun mapEntityToDomain(
        entity: RepositoryGitItemEntity
    ): RepositoryGitItem {
        return RepositoryGitItem(
            id = entity.id,
            name = entity.name,
            owner = RepositoryGitOwner(
                entity.owner,
                entity.avatarUrl,
            ),
            starCount = entity.starCount,
            forkCount = entity.forkCount,
        )
    }

    override suspend fun mapRemoteToEntity(
        remote: RepositoryGitItemResponse
    ): RepositoryGitItemEntity {
        return RepositoryGitItemEntity(
            id = remote.id,
            name = remote.name,
            owner = remote.owner.login,
            avatarUrl = remote.owner.avatarUrl,
            starCount = remote.starCount,
            forkCount = remote.forkCount,
        )
    }

    override suspend fun mapRemoteToDomain(
        remote: RepositoryGitItemResponse
    ): RepositoryGitItem {
        return RepositoryGitItem(
            id = remote.id,
            name = remote.name,
            owner = RepositoryGitOwner(
                remote.owner.login,
                remote.owner.avatarUrl,
            ),
            starCount = remote.starCount,
            forkCount = remote.forkCount,
        )
    }

    override suspend fun mapDomainListToUI(
        domainList: List<RepositoryGitItem>
    ): List<RepositoryGitItemUI> {
        return withContext(defaultDispatcher) {
            domainList.map {
                mapDomainToUI(it)
            }
        }
    }

    override suspend fun mapDomainToUI(
        domain: RepositoryGitItem
    ): RepositoryGitItemUI {
        return RepositoryGitItemUI(
            id = domain.id,
            name = domain.name,
            owner = RepositoryGitOwnerUI(
                domain.owner.login,
                domain.owner.avatarUrl,
            ),
            starCount = domain.starCount,
            forkCount = domain.forkCount,
        )
    }

}