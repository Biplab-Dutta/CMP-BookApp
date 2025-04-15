package com.zoroxnekko.bookapp.feature.book.data.repository

import com.zoroxnekko.bookapp.core.domain.DataError
import com.zoroxnekko.bookapp.core.domain.Result
import com.zoroxnekko.bookapp.core.domain.map
import com.zoroxnekko.bookapp.feature.book.data.mappers.toBook
import com.zoroxnekko.bookapp.feature.book.data.network.RemoteBookDataSource
import com.zoroxnekko.bookapp.feature.book.domain.Book
import com.zoroxnekko.bookapp.feature.book.domain.BookRepository

class BookRepositoryImpl(
    private val remoteBookDataSource: RemoteBookDataSource
) : BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }
}