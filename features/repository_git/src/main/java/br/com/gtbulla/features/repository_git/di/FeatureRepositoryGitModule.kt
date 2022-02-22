package br.com.gtbulla.features.repository_git.di

import br.com.gtbulla.features.repository_git.data.datasource.RepositoryDataSource
import br.com.gtbulla.features.repository_git.data.datasource.RepositoryDataSourceImpl
import br.com.gtbulla.features.repository_git.data.mediator.PageDataStore
import br.com.gtbulla.features.repository_git.data.mediator.RepositoryMediator
import br.com.gtbulla.features.repository_git.data.repository.RepoGitRepository
import br.com.gtbulla.features.repository_git.data.repository.RepoGitRepositoryImpl
import br.com.gtbulla.features.repository_git.data.service.GithubService
import br.com.gtbulla.features.repository_git.presenter.viewmodel.RepositoryViewModel
import br.com.gtbulla.libraries.common.model.mapper.RepositoryGitMapper
import br.com.gtbulla.libraries.common.model.mapper.RepositoryGitMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val featureRepositoryGitModule = module {

    single<RepositoryGitMapper> {
        RepositoryGitMapperImpl(defaultDispatcher = get(named("defaultDispatcher")))
    }

    factory { provideGithubService(retrofit = get()) }

    factory { PageDataStore(context = get()) }

    factory<RepositoryDataSource> {
        RepositoryDataSourceImpl(
            pageDataStore = get(),
            mapper = get(),
            service = get(),
            database = get(),
        )
    }

    factory {
        RepositoryMediator(
            pageDataStore = get(),
            mapper = get(),
            service = get(),
            database = get(),
        )
    }

    factory<RepoGitRepository> {
        RepoGitRepositoryImpl(
            dataSource = get(),
            mapper = get(),
        )
    }

    viewModel { RepositoryViewModel(repository = get(), mapper = get()) }

}

internal fun provideGithubService(
    retrofit: Retrofit
): GithubService = retrofit.create(GithubService::class.java)