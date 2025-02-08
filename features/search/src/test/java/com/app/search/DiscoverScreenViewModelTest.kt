package com.app.search

import com.app.search.data.SearchNewsContract
import com.app.search.model.NewsItemData
import com.app.search.model.SourceData
import com.app.search.remote.NewsContractImpl
import com.app.search.ui.presentation.DiscoverScreenViewModel
import io.mockk.mockk
import okhttp3.OkHttpClient
import org.junit.Before

/**
 * Created by {EUNICE BAKARE T.} on {4/28/24}
 * Email: {eunice@reach.africa}
 */

val newsItemData = listOf<NewsItemData>(
    NewsItemData(
        title = "What Training Do Volleyball Players Need?",
        description = "How can we scale the training involved in Volleyball?",
        image = "",
        category = "Sport",
        author = "James McKindey",
        authorImg = null,
        datePosted = "2023-03-27",
        source = SourceData(id = "abc-news", name = "ABC News"),
        content = "",
        url = ""
    ),
    NewsItemData(
        title = "Secondary School bullying: How to curtail among students",
        description = "How can we manage the bullying that spreads among students?",
        image = "",
        category = "Education",
        author = "Rosemary Nkem",
        authorImg = null,
        datePosted = "2023-03-25",
        source = SourceData(id = "cnn", name = "CNN"),
        content = "",
        url = ""
    ),
    NewsItemData(
        title = "Layoffs rampant in Big Tech: Employees are scrambling to save their jobs.",
        description = "There has been a flood of layoffs across Big Tech, leaving many jobless and others worried about their jobs",
        image = "",
        category = "Tech",
        author = "Lisa Rain",
        authorImg = null,
        datePosted = "2023-03-28",
        source = SourceData(id = "tech-news", name = "Tech News"),
        content = "",
        url = ""
    ),
    NewsItemData(
        title = "Is Russia and Ukraine finally coming to a standstill?",
        description = "Russia and Ukraine may finally be ending the war!",
        image = "",
        author = "Roberts Chin",
        authorImg = null,
        datePosted = "2023-03-30",
        source = SourceData(id = "world-news", name = "World News"),
        content = "",
        url = ""
    )
)
class DiscoverScreenViewModelTest {
    lateinit var viewModel: DiscoverScreenViewModel

    @Before
    fun setup() {
        val newsContract = NewsContractImpl(
            lazy { OkHttpClient() }
        )
        viewModel = DiscoverScreenViewModel(newsContract)
    }

    fun viewModel_returnsCorrectSearchResult() {
        val searchQuery = "Secondary"

        viewModel.searchNews(searchQuery)

    }

    fun viewModel_returnsEmptySearchResult() {
        val searchQuery = "khjjjgh"

        viewModel.searchNews(searchQuery)
    }

    fun viewModel_returnsSearchResult_withRelevancySorting() {

    }
}