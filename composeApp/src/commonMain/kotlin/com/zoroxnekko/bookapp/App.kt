package com.zoroxnekko.bookapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.zoroxnekko.bookapp.feature.book.presentation.book_list.BookListScreenRoot
import com.zoroxnekko.bookapp.feature.book.presentation.book_list.BookListViewModel

@Composable
fun App() {
    BookListScreenRoot(
        viewModel = remember { BookListViewModel() },
        onBookClick = {}
    )
}