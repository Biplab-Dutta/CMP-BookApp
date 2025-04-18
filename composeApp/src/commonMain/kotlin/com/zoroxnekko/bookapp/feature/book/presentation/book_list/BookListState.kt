package com.zoroxnekko.bookapp.feature.book.presentation.book_list

import com.zoroxnekko.bookapp.core.presentation.UiText
import com.zoroxnekko.bookapp.feature.book.domain.Book

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null,
)
