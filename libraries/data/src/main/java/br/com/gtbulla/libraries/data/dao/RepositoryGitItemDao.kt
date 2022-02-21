package br.com.gtbulla.libraries.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import br.com.gtbulla.libraries.common.model.entity.RepositoryGitItemEntity

@Dao
interface RepositoryGitItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(list: List<RepositoryGitItemEntity>)

    @Query("SELECT * FROM repository_git_item ORDER BY star_count DESC")
    fun getList(): PagingSource<Int, RepositoryGitItemEntity>

    @Query("DELETE FROM repository_git_item")
    fun deleteAll()

}