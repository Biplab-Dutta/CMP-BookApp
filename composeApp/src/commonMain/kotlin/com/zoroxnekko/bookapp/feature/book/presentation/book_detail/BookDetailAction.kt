package com.zoroxnekko.bookapp.feature.book.presentation.book_detail

import com.zoroxnekko.bookapp.feature.book.domain.Book

sealed interface BookDetailAction {
    data object OnBackClick : BookDetailAction
    data object OnFavoriteClick : BookDetailAction
    data class OnSelectedBookChange(val book: Book) : BookDetailAction
}