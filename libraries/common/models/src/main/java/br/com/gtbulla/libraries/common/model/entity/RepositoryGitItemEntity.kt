package br.com.gtbulla.libraries.common.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.gtbulla.libraries.common.model.domain.RepositoryGitItem

@Entity(tableName = "repository_git_item")
class RepositoryGitItemEntity constructor(
    @PrimaryKey
    val id: Long,
    val name: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "fork_count")
    val forkCount: Int,
    val owner: String,
    @ColumnInfo(name = "star_count")
    val starCount: Int,
)

fun RepositoryGitItem.asEntity(): RepositoryGitItemEntity {
    return RepositoryGitItemEntity(
        id = id,
        avatarUrl = owner.avatarUrl,
        forkCount = forkCount,
        name = name,
        owner = owner.login,
        starCount = starCount,
    )
}

fun List<RepositoryGitItem>.asEntity(): List<RepositoryGitItemEntity> {
    return map {
        it.asEntity()
    }
}