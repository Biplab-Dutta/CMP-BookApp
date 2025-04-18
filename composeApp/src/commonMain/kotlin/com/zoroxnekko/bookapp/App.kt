package com.zoroxnekko.bookapp

import androidx.compose.runtime.Composable
import com.zoroxnekko.bookapp.feature.book.presentation.book_list.BookListScreenRoot
import com.zoroxnekko.bookapp.feature.book.presentation.book_list.BookListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel = koinViewModel<BookListViewModel>()
    BookListScreenRoot(
        viewModel = viewModel,
        onBookClick = {}
    )
}