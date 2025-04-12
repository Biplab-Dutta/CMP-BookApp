package com.zoroxnekko.bookapp.feature.book.presentation.book_list

import com.zoroxnekko.bookapp.core.presentation.UiText
import com.zoroxnekko.bookapp.feature.book.domain.Book

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = dummyBooks,
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null,
)

val dummyBooks = (1..100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://icons.veryicon.com/png/o/business/colorful-office-icons/book-52.png",
        authors = listOf("Philipp Lackner"),
        description = "Description $it",
        languages = emptyList(),
        firstPublishYear = null,
        averageRating = 4.67854,
        ratingCount = 5,
        numPages = 100,
        numEditions = 3
    )
}