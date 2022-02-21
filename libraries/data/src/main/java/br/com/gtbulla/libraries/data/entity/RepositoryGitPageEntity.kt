package br.com.gtbulla.libraries.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository_git_page")
class RepositoryGitPageEntity constructor(
    @PrimaryKey val page: Int,
)
