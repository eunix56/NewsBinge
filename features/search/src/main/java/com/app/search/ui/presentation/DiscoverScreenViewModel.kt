package com.app.search.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.search.data.SearchNewsContract
import com.app.search.model.NewsItemData
import com.app.search.remote.model.NewsData.Companion.toNewsItemData
import com.app.search.remote.model.NewsSourceModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by {EUNICE BAKARE T.} on {3/22/24}
 * Email: {eunice@reach.africa}
 */

class DiscoverScreenViewModel(
    private val newsContract: SearchNewsContract
): ViewModel() {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    val categoryUiState: StateFlow<CategoryUiState> = _categoryUiState.asStateFlow()

    private val _sortValue = MutableStateFlow(String())

    private lateinit var newsSources: List<NewsSourceModel>

    private val searchNewsExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _searchUiState.update { it.copy(
            loading = false,
            error = throwable.message
        ) }
    }

    private val categoryExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _categoryUiState.update { it.copy(
            loading = false,
            error = throwable.message
        ) }
    }

    init {
        getCategories()
    }

    fun setSortValue(sortValue: String) {
        _sortValue.value = sortValue
    }

    private fun getCategories() {
        viewModelScope.launch(categoryExceptionHandler) {
            _categoryUiState.update { it.copy(loading = true) }
            val sources = newsContract.getSources()
            newsSources = sources.source
            _categoryUiState.update {
                it.copy(
                    loading = false,
                    success = sources.source.map { newsSourceModel -> newsSourceModel.category } + "World"
                ) }
        }
    }

    fun selectCategory(category: String) {
        viewModelScope.launch {
            _searchUiState.update {
                it.copy(loading = false,
                    success = it.success.filter { newsItemData ->  newsItemData.category == category })
            }
        }
    }

    fun searchNews(query: String) {
        viewModelScope.launch(searchNewsExceptionHandler) {
            _searchUiState.update {
                it.copy(
                    loading = true
                )
            }
            val searchResults = if (_sortValue.value.isEmpty()) {
                newsContract.searchNews(query, null)
            } else {
                newsContract.searchNews(query, _sortValue.value)
            }
            val newsItemData = searchResults.articles.map { it.toNewsItemData() }

            newsItemData.forEach { newsItem ->
                val source = newsSources.find { it.id == newsItem.source.id }
                if (source != null) newsItem.category = source.category
            }

            _searchUiState.update {
                it.copy(loading = false,
                    success = newsItemData)
            }
        }
    }


    data class CategoryUiState(
        val loading: Boolean = false,
        val success: List<String> = emptyList(),
        val error: String? = null
    )

    data class SearchUiState(
        val loading: Boolean = false,
        val success: List<NewsItemData> = emptyList(),
        val error: String? = null
    )
}