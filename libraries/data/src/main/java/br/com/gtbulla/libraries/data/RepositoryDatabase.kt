package br.com.gtbulla.libraries.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.gtbulla.libraries.common.model.entity.RepositoryGitItemEntity
import br.com.gtbulla.libraries.data.dao.RepositoryGitItemDao
import br.com.gtbulla.libraries.data.dao.RepositoryGitPageDao
import br.com.gtbulla.libraries.data.entity.RepositoryGitPageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        RepositoryGitItemEntity::class,
        RepositoryGitPageEntity::class,
    ], version = 3, exportSchema = true
)
abstract class RepositoryDatabase : RoomDatabase() {
    abstract val repositoryGitItemDao: RepositoryGitItemDao
    abstract val repositoryGitPageDao: RepositoryGitPageDao
}

private lateinit var INSTANCE: RepositoryDatabase

fun getDatabase(context: Context): RepositoryDatabase {
    synchronized(RepositoryDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                RepositoryDatabase::class.java,
                "repository_database.db"
            ).fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}

fun RepositoryDatabase.clearDatabase() {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            repositoryGitPageDao.deleteAll()
            repositoryGitItemDao.deleteAll()
        } catch (ex: Exception) {
            Log.e("Database-Error", ex.toString())
        }
    }
}