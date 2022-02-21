package br.com.gtbulla.starkotlin.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutinesModule = module {
    factory(named("defaultDispatcher")) { Dispatchers.Default }
    factory(named("ioDispatcher")) { Dispatchers.IO }
    factory(named("mainDispatcher")) { Dispatchers.Main }
}