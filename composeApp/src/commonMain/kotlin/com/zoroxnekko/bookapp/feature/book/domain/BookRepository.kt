package com.zoroxnekko.bookapp.feature.book.domain

import com.zoroxnekko.bookapp.core.domain.DataError
import com.zoroxnekko.bookapp.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
}