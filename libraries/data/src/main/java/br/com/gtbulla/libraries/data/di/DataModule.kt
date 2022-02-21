package br.com.gtbulla.libraries.data.di

import br.com.gtbulla.libraries.data.RepositoryDatabase
import br.com.gtbulla.libraries.data.getDatabase
import br.com.gtbulla.libraries.data.getRetrofit
import br.com.gtbulla.libraries.data.response.ResponseError
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.koin.dsl.module

val dataModule = module {

    single { getDatabase(context = get()) }
    single { get<RepositoryDatabase>().repositoryGitItemDao }
    single { provideMoshi() }
    single { provideJsonErrorAdapter(moshi = get()) }
    single { getRetrofit() }
}

internal fun provideMoshi(): Moshi {
    return Moshi.Builder().build()
}

fun provideJsonErrorAdapter(moshi: Moshi): JsonAdapter<ResponseError> {
    return moshi.adapter(ResponseError::class.java)
}