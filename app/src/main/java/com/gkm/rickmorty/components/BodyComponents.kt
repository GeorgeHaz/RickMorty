package com.gkm.rickmorty.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gkm.rickmorty.R

@Composable
fun MainTopBar(
    Title: @Composable () -> Unit,
    showBackButton: Boolean = false,
    showSearchButton: Boolean = false,
    showImage: Boolean = false,
    onClickBackButton: () -> Unit,
    onClickAction: () -> Unit,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
    {
        Column {
            Box(modifier = Modifier) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (showBackButton) {
                        IconButton(
                            onClick = { onClickBackButton() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                    Title()
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (showImage) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(vertical = 20.dp),
                        painter = painterResource(id = R.drawable.rickmorty),
                        contentDescription = "Logo"
                    )
                }
            }
            if (showSearchButton) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.BottomEnd),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(Color.White)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .fillMaxWidth()
                        ) {
                            TextButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { onClickAction() }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search",
                                    tint = Color.Black
                                )
                                Text(
                                    text = "Buscar personajes",
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonNav(
    tittle: String,
    clickNav: () -> Unit,
    modifier: Modifier,
) {
    OutlinedButton(
        onClick = { clickNav() },
        modifier = modifier,
        border = BorderStroke(
            width = 3.dp,
            color = MaterialTheme.colorScheme.tertiary
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = tittle,
            color = MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun CustomSearchBar(
    value: String,
    placeholder: String,
    navigateUp: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    val requester = remember { FocusRequester() }
    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
            }
            TextField(
                value = value,
                onValueChange = { name ->
                    onValueChange(name)
                },
                placeholder = {
                    Text(text = placeholder)
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
                    .focusRequester(
                        focusRequester = requester
                    )
                    .background(Color.Transparent),
                trailingIcon = {
                    if (value.isNotBlank()) {
                        IconButton(onClick = {
                            onValueChange("")
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear, contentDescription = "clear Search",
                                modifier = Modifier.padding(end = 8.dp)
                                    .size(20.dp)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
        }

        Divider(modifier = Modifier.fillMaxWidth())
    }
    SideEffect {

        requester.requestFocus()
    }
}