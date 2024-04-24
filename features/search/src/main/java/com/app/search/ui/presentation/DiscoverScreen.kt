package com.app.search.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.app.search.R
import com.app.search.model.NewsItemData
import com.app.search.model.SourceData
import com.app.search.ui.shimmerLoadingAnimation
import com.app.search.ui.theme.NewsBingeTheme

/**
 * Created by {EUNICE BAKARE T.} on {2/21/24}
 * Email: {eunice@reach.africa}
 */

@Composable
fun DiscoverScreen(viewModel: DiscoverScreenViewModel = viewModel()) {
    val newsItemUiState by viewModel.searchUiState.collectAsState()
    val categoryUiState by viewModel.categoryUiState.collectAsState()

    DiscoverScreenContent(
        chips = categoryUiState.success,
        chipIsLoading = categoryUiState.loading,
        newsItemData = newsItemUiState.success,
        newsIsLoading = newsItemUiState.loading,
        { viewModel.searchNews(it) },
        { viewModel.selectCategory(it) },
        { viewModel.setSortValue(it) })
}

@Composable
fun DiscoverScreenContent(chips: List<String>, 
                          chipIsLoading: Boolean,
                          newsItemData: List<NewsItemData>,
                          newsIsLoading: Boolean,
                          onSearch: (String) -> Unit,
                          onSelected: (String) -> Unit,
                          onSort: (String) -> Unit) {
    val context = LocalContext.current
    val selectedChip = remember { mutableStateOf("All") }
    val selectedSortValue = remember { mutableStateOf(context.getString(R.string.publishedat)) }

    Box(modifier = Modifier.background(
        color = Color.White
    )) {
        Column {
            DiscoverHeader()
            DiscoverSearchBar (
                { onSearch(it) },
                { sortValue ->
                    selectedSortValue.value = sortValue
                    onSort(sortValue)
                },
                selectedSortValue.value)
            if (chipIsLoading) {
                CustomChipLoader(modifier = Modifier.shimmerLoadingAnimation())
            } else {
                CategoryChips(
                    modifier = Modifier
                        .padding(16.dp, 2.dp),
                    chips = chips, selectedChip.value
                ) {
                    selectedChip.value = it
                    onSelected(it)
                }
            }
            if (newsIsLoading) {
                NewsItemLoader(modifier = Modifier.shimmerLoadingAnimation())
            } else {
                NewsList(newsItemData = newsItemData)
            }
        }
    }
}

@Composable
fun DiscoverHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp, 12.dp)
            .fillMaxWidth())  {
        Text(
            text = stringResource(R.string.discover),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.news_from_all_around_the_world),
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun DiscoverSearchBar(
    onSearch: (String) -> Unit,
    onSort: (String) -> Unit,
    selectedValue: String
) {
    val openDialog = remember { mutableStateOf(false) }

    CustomSearchBar(
        query = stringResource(R.string.search),
        onQueryChange = {},
        onSearch = onSearch,
        active = false,
        onActiveChange = {},
        modifier = Modifier
            .padding(20.dp, 16.dp),
        leadingIcon = {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_search
                ),
                contentDescription = stringResource(R.string.tune_icon_by)
            )
        },
        trailingIcon = {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_tune),
                contentDescription = null,
                modifier = Modifier.clickable { openDialog.value = true }
            )
        }
    )

    if (openDialog.value) {
        SortDialog(openDialog = openDialog, onSort = onSort, selectedValue = selectedValue)
    }
}

@Composable
fun SortDialog(openDialog: MutableState<Boolean>,
               onSort: (String) -> Unit,
               selectedValue: String) {
    val context = LocalContext.current
    val sortList = listOf<String>(
        stringResource(id = R.string.relevancy),
        stringResource(id = R.string.popularity),
        stringResource(id = R.string.publishedat)
    )

    Dialog(onDismissRequest = { openDialog.value = false }) {
        Card(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .clip(RoundedCornerShape(3.dp))
                .fillMaxWidth(0.8F)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp, 8.dp)
            ) {
                items(sortList) {
                    if (selectedValue == it) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = it,
                                modifier = Modifier
                                    .padding(4.dp, 6.dp),
                                style = MaterialTheme.typography.bodyMedium)

                            Spacer(modifier = Modifier.weight(1F))

                            Image(
                                painter = painterResource(id = R.drawable.ic_checked),
                                contentDescription = stringResource(id = R.string.selected),
                                modifier = Modifier.align(Alignment.CenterVertically))
                        }
                    } else {
                        Text(text = it,
                            modifier = Modifier
                                .padding(4.dp, 6.dp)
                                .clickable {
                                    openDialog.value = false
                                    onSort(it.lowercase())
                                },
                            style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryChips(modifier: Modifier = Modifier,
                  chips: List<String>,
                  selectedChip: String,
                  onSelected: (String) -> Unit) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(6.dp)) {
        chips.forEach { item ->
            CustomChip(
                selected = item == selectedChip,
                onSelected = onSelected,
                text = item)
        }
    }
}

@Composable
fun NewsList(newsItemData: List<NewsItemData>) {
    Box {
        LazyColumn {
            items(newsItemData) {
                NewsItem(newsItem = it)
            }
        }
    }
}

@Composable
fun NewsItem(newsItem: NewsItemData,
             modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier
            .size(120.dp)
        ) {
            AsyncImage(
                model = newsItem.image,
                placeholder = painterResource(id = R.drawable.news_placeholder),
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.news_img)
            )
        }

        Column(
            modifier = Modifier
                .height(120.dp)
                .padding(2.dp, 4.dp)
        ) {
            Text(
                text = newsItem.category,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(0.dp, 2.dp, 0.dp, 6.dp)
            )

            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.headlineMedium,
            )

            Spacer(modifier = Modifier.weight(1F))

            Row {
                AsyncImage(
                    model = newsItem.authorImg,
                    placeholder = painterResource(id = R.drawable.author_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(24.dp),
                    contentDescription = stringResource(id = R.string.news_img)
                )

                Text(
                    text = newsItem.author,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(8.dp, 0.dp)
                )

                Spacer(modifier = Modifier.weight(1F))

                Text(
                    text = newsItem.datePosted,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .align(Alignment.CenterVertically))
            }
        }
    }
}

@Composable
fun CustomChip(
    selected: Boolean,
    onSelected: (String) -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = when {
                selected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.secondary
        },
        contentColor = when {
            selected -> MaterialTheme.colorScheme.onPrimary
            else -> MaterialTheme.colorScheme.onSecondary
        },
        shape = RoundedCornerShape(14.dp),
        onClick = { onSelected(text) }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(13.dp, 6.dp))
    }

}

@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeHolder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (()->Unit)? = null,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = MaterialTheme.typography.labelMedium,
            enabled = enabled,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
            singleLine = true,
            modifier = Modifier
                .height(50.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { onActiveChange(it.isFocused) }
                .semantics {
                    onClick {
                        focusRequester.requestFocus()
                        true
                    }
                },
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(size = 30.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp) // inner padding

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        ) {
                            leadingIcon?.let { it() }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box {
                                if (query.isEmpty()) {
                                    placeHolder?.let { it() }
                                }
                                innerTextField()
                            }
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                        ) {
                            trailingIcon?.let { it() }
                        }
                    }
                }
            }
        )
        LaunchedEffect(active) {
            if (!active) {
                focusManager.clearFocus()
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomChipLoader(modifier: Modifier) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.Center,
        maxItemsInEachRow = 5,
        modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)
    ) {
        repeat(5) {
            Box(modifier = modifier
                .size(64.dp, 36.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.LightGray))
        }
    }
}

@Composable
fun NewsItemLoader(modifier: Modifier) {
    repeat(6) {
        Row(modifier = Modifier.padding(16.dp, 12.dp, 0.dp, 0.dp)) {
            Box(modifier = modifier
                .size(120.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = Color.LightGray)
            )

            Column(modifier = modifier
                .height(120.dp)
                .padding(8.dp, 0.dp, 8.dp, 0.dp)) {
                Box(modifier = modifier
                    .size(100.dp, 24.dp)
                    .padding(4.dp)
                    .background(color = Color.LightGray))

                Box(modifier = modifier
                    .size(250.dp, 52.dp)
                    .padding(4.dp)
                    .background(color = Color.LightGray))

                Spacer(modifier = modifier.weight(1F))

                Row {
                    Box(modifier = modifier
                        .size(100.dp, 24.dp)
                        .padding(4.dp)
                        .background(color = Color.LightGray))

                    Spacer(modifier = modifier.weight(1F))

                    Box(modifier = modifier
                        .size(100.dp, 24.dp)
                        .padding(4.dp)
                        .background(color = Color.LightGray))
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    NewsBingeTheme {
//        DiscoverScreenContent("All Sports Law Politics Education".split(" "),
//            true,
//            listOf(
//                NewsItemData(
//                    "What training do Volleyball Players Need?",
//                    "",
//                    "",
//                    "Sport",
//                    "Eunice B",
//                    null,
//                    "12-03-2024",
//                    SourceData("",""),
//                    "",
//                    ""
//                )
//            ), true, {}, {}, {})
        val openDialog = remember {
            mutableStateOf(false)
        }
        SortDialog(openDialog = openDialog, onSort = {}, selectedValue = "Published At")
    }
}