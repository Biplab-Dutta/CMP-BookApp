package com.zoroxnekko.bookapp.feature.book.presentation.book_detail

import com.zoroxnekko.bookapp.feature.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val book: Book? = null,
)
