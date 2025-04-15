package com.zoroxnekko.bookapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.zoroxnekko.bookapp.core.data.HttpClientFactory
import com.zoroxnekko.bookapp.feature.book.data.network.RemoteBookDataSource
import com.zoroxnekko.bookapp.feature.book.data.repository.BookRepositoryImpl
import com.zoroxnekko.bookapp.feature.book.presentation.book_list.BookListScreenRoot
import com.zoroxnekko.bookapp.feature.book.presentation.book_list.BookListViewModel
import io.ktor.client.engine.HttpClientEngine

@Composable
fun App(engine: HttpClientEngine) {
    BookListScreenRoot(
        viewModel = remember {
            BookListViewModel(
                BookRepositoryImpl(
                    RemoteBookDataSource(
                        HttpClientFactory.create(engine)
                    ),
                )
            )
        },
        onBookClick = {}
    )
}