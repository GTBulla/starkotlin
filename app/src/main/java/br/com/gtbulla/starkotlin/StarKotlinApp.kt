package br.com.gtbulla.starkotlin

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import br.com.gtbulla.starkotlin.di.coroutinesModule
import br.com.gtbulla.features.repository_git.di.featureRepositoryGitModule
import br.com.gtbulla.libraries.data.clearDatabase
import br.com.gtbulla.libraries.data.di.dataModule
import br.com.gtbulla.libraries.data.getDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class StarKotlinApp : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()

        var list = mutableListOf(
            coroutinesModule,
            dataModule,
            featureRepositoryGitModule,
        )
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@StarKotlinApp)
            modules(list)
        }

        getDatabase(this@StarKotlinApp).clearDatabase()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

}