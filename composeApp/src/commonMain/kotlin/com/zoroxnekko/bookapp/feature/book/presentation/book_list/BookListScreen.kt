package com.zoroxnekko.bookapp.feature.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookapp.composeapp.generated.resources.Res
import bookapp.composeapp.generated.resources.favorites
import bookapp.composeapp.generated.resources.no_favorite_books
import bookapp.composeapp.generated.resources.no_search_results
import bookapp.composeapp.generated.resources.search_results
import com.zoroxnekko.bookapp.core.presentation.DarkBlue
import com.zoroxnekko.bookapp.core.presentation.DesertWhite
import com.zoroxnekko.bookapp.core.presentation.SandYellow
import com.zoroxnekko.bookapp.feature.book.domain.Book
import com.zoroxnekko.bookapp.feature.book.presentation.book_list.components.BookListView
import com.zoroxnekko.bookapp.feature.book.presentation.book_list.components.BookSearchBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookListAction.OnBookClick -> onBookClick(action.book)
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
private fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState { 2 }
    val searchResultsListState = rememberLazyListState()
    val favoriteBooksListState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        searchResultsListState.animateScrollToItem(0)
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListAction.OnTabSelected(pagerState.currentPage))
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(DarkBlue),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BookSearchBar(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .fillMaxWidth()
                    .padding(16.dp),
                searchQuery = state.searchQuery,
                onSearchQueryChange = {
                    onAction(BookListAction.OnSearchQueryChange(it))
                },
                onImeSearch = {
                    keyboardController?.hide()
                },
            )
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                color = DesertWhite,
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TabRow(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .widthIn(max = 700.dp)
                            .fillMaxWidth(),
                        selectedTabIndex = state.selectedTabIndex,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                color = SandYellow,
                                modifier = Modifier.tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                            )
                        },
                    ) {
                        CoreTab(
                            modifier = Modifier.weight(1f),
                            isSelected = state.selectedTabIndex == 0,
                            text = stringResource(Res.string.search_results),
                            onClick = {
                                onAction(BookListAction.OnTabSelected(0))
                            },
                        )
                        CoreTab(
                            modifier = Modifier.weight(1f),
                            isSelected = state.selectedTabIndex == 1,
                            text = stringResource(Res.string.favorites),
                            onClick = {
                                onAction(BookListAction.OnTabSelected(1))
                            },
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) { pageIndex ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            when (pageIndex) {
                                0 -> {
                                    if (state.isLoading) {
                                        CircularProgressIndicator()
                                    } else {
                                        when {
                                            state.errorMessage != null -> {
                                                Text(
                                                    text = state.errorMessage.asString(),
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    color = MaterialTheme.colorScheme.error,
                                                )
                                            }

                                            state.searchResults.isEmpty() -> {
                                                Text(
                                                    text = stringResource(Res.string.no_search_results),
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    color = MaterialTheme.colorScheme.error,
                                                )
                                            }

                                            else -> {
                                                BookListView(
                                                    modifier = Modifier.fillMaxSize(),
                                                    books = state.searchResults,
                                                    onBookClick = {
                                                        onAction(BookListAction.OnBookClick(it))
                                                    },
                                                    scrollState = searchResultsListState,
                                                )
                                            }
                                        }
                                    }
                                }

                                1 -> {
                                    if (state.favoriteBooks.isEmpty()) {
                                        Text(
                                            text = stringResource(Res.string.no_favorite_books),
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.headlineSmall,
                                        )
                                    } else {
                                        BookListView(
                                            modifier = Modifier.fillMaxSize(),
                                            books = state.favoriteBooks,
                                            onBookClick = {
                                                onAction(BookListAction.OnBookClick(it))
                                            },
                                            scrollState = favoriteBooksListState,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CoreTab(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    text: String,
    onClick: () -> Unit,
) {
    Tab(
        modifier = modifier,
        selected = isSelected,
        onClick = onClick,
        selectedContentColor = SandYellow,
        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
    ) {
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = text,
        )
    }
}
