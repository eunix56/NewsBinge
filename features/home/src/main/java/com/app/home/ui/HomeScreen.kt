package com.app.home.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.app.common.model.NewsItemData
import com.app.common.theme.Blue800
import com.app.common.theme.Grey150
import com.app.common.theme.NewsBingeTheme
import com.app.home.R
import kotlin.math.absoluteValue

/**
 * Created by {EUNICE BAKARE T.} on {5/21/24}
 * Email: {eunice@reach.africa}
 */

@Composable
fun HomeScreen() {

}

@Composable
fun HomeScreenContent() {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsCarousel(list: List<Int>) {
    val state = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { list.size }

    Column {
        HorizontalPager(state = state) {
            Image(painter = painterResource(it),
                contentDescription = null)
        }

        DotIndicators(state.pageCount, state.currentPage)
    }
}

@Composable
fun DotIndicators(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(pageCount) { iteration ->
            DotView(currentPage == iteration)
        }
    }
}

@Composable
fun DotView(
    isSelected: Boolean,
) {
    val defaultRadius = 9.dp
    val selectedRadius = 27.dp

    val color: Color by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondary
        },
        animationSpec = tween(durationMillis = 200),
        label = "dot color"
    )

    val width: Dp by animateDpAsState(
        targetValue = if (isSelected) selectedRadius else defaultRadius,
        animationSpec = tween(durationMillis = 200),
        label = "dot width"
    )

    Canvas(modifier = Modifier.size(
        width = width, height = defaultRadius
    )) {
        drawRoundRect(
            color = color,
            size = Size(
                width = width.toPx(),
                height = defaultRadius.toPx()
            ),
            cornerRadius = CornerRadius(
                x = defaultRadius.toPx(),
                y = defaultRadius.toPx()
            )
        )
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
             modifier: Modifier = Modifier
) {
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

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.carouselTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

        val transformation =
            lerp(
                start = 0.7f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        alpha = transformation
        scaleY = transformation
    }


@Composable
@Preview(showBackground = true)
fun DefaultPreview() {
    NewsBingeTheme {
        NewsCarousel(
            listOf(
                R.drawable.news_placeholder,
                R.drawable.author_placeholder
            )
        )
    }
}
