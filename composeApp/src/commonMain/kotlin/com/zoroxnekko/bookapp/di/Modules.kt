package com.zoroxnekko.bookapp.di

import com.zoroxnekko.bookapp.core.data.HttpClientFactory
import com.zoroxnekko.bookapp.feature.book.data.network.RemoteBookDataSource
import com.zoroxnekko.bookapp.feature.book.data.repository.BookRepositoryImpl
import com.zoroxnekko.bookapp.feature.book.domain.BookRepository
import com.zoroxnekko.bookapp.feature.book.presentation.SelectedBookViewModel
import com.zoroxnekko.bookapp.feature.book.presentation.book_list.BookListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::RemoteBookDataSource)
    singleOf(::BookRepositoryImpl).bind<BookRepository>()

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
}