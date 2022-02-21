package br.com.gtbulla.libraries.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.gtbulla.libraries.data.entity.RepositoryGitPageEntity

@Dao
interface RepositoryGitPageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(key: RepositoryGitPageEntity)

    @Query("SELECT * FROM repository_git_page ORDER BY page DESC LIMIT 1")
    suspend fun getPage(): RepositoryGitPageEntity?

    @Query("DELETE FROM repository_git_page")
    fun deleteAll()
}