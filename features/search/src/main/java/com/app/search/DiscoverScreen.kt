package com.app.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by {EUNICE BAKARE T.} on {2/21/24}
 * Email: {eunice@reach.africa}
 */

@Composable
fun DiscoverScreen() {
    Box(modifier = Modifier.background(
        color = Color.White
    )) {
        Column {
            DiscoverHeader(
                modifier = Modifier
                    .padding(2.dp)
            )
            DiscoverSearchBar()
            CategoryChips(chips = listOf("All, Sports, Law"))
        }
    }
}

@Composable
fun DiscoverHeader(modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.discover),
            color = Color.Black,
            modifier = Modifier
                .size(24.dp)
                .padding(20.dp)
        )
        Text(
            text = stringResource(R.string.news_from_all_around_the_world),
            color = Color.LightGray,
            modifier = Modifier
                .size(14.dp)
                .padding(20.dp, 12.dp)
        )
    }
}

@Composable
fun DiscoverSearchBar() {
    CustomSearchBar(
        query = stringResource(R.string.search),
        onQueryChange = {},
        onSearch = {},
        active = false,
        onActiveChange = {},
        modifier = Modifier
            .padding(20.dp, 28.dp),
        leadingIcon = {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_search),
                contentDescription = stringResource(R.string.search)
            )
        }
    )
}

@Composable
fun CategoryChips(chips: List<String>) {
    val selectedIndex = remember { mutableStateOf(0) }
    LazyRow {
        itemsIndexed(chips) { index, item ->
            CustomChip(
                selected = index == selectedIndex.value,
                onSelected = { selectedIndex.value = index },
                text = item)
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
    Button(
        modifier = modifier
            .padding(3.dp, 12.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = when {
                selected -> MaterialTheme.colors.onSurface
                else -> MaterialTheme.colors.secondary
            },
            contentColor = when {
                selected -> Color.White
                else -> Color.DarkGray
            }

        ),
        elevation = null,
        shape = RoundedCornerShape(8.dp),
        onClick = { onSelected(text) }
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(6.dp, 2.dp))
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
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            ),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
            singleLine = true,
            modifier = Modifier
                .height(56.dp)
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
                            color = Color(0XFFF6F6F7),
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DiscoverScreen()
}