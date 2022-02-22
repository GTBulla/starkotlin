package br.com.gtbulla.features.repository_git.data.mediator

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class PageDataStore(private val context: Context) {

    suspend fun save(pageIndex: Int) {
        context.page.edit { preferences->
            preferences[PAGE_KEY] = pageIndex
        }
    }

    val currentPage =
        context.page.data.map { preferences ->
            preferences[PAGE_KEY] ?: 1
        }

    companion object {
        private const val DATASTORE_NAME = "page_preferences"

        private val PAGE_KEY = intPreferencesKey("page_key");

        private val Context.page by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}