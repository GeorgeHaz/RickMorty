package com.gkm.rickmorty.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gkm.rickmorty.R
import com.gkm.rickmorty.ui.theme.RickMortyTheme

@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    button: ImageVector,
    showButton: Boolean = false,
    onClickBackButton: () -> Unit,
    showImage: Boolean = false,
    imageApp: Painter = painterResource(id = R.drawable.rickmorty),
    showSearch: Boolean = false,
    onClickAction: () -> Unit,
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showButton) {
                IconButton(
                    onClick = { onClickBackButton() }
                ) {
                    Icon(
                        imageVector = button,
                        contentDescription = "back"
                    )
                }
            }
            title()
        }
        if (showImage) {
            Box(
                modifier = Modifier
                    .height(150.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = imageApp,
                    contentDescription = "logo_rickMorty",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(80.dp),
                    alignment = Alignment.Center
                )
            }
        }
        Spacer(Modifier.size(8.dp))
        if (showSearch) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.BottomEnd),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
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
                                contentDescription = "search"
                            )
                            Text(
                                text = stringResource(id = R.string.search_character),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    icon:ImageVector,
    value: String="",
    placeHolder:@Composable () -> Unit,
    navigateUp: () -> Unit,
    onValueChange: (String) -> Unit,
){
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ){
            IconButton(
                onClick = { navigateUp() }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "back"
                )
            }
            TextField(
                value = value,
                onValueChange =onValueChange,
                placeholder = placeHolder,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onValueChange(value)
                    }),
                trailingIcon = {
                    if(value.isNotEmpty()){
                        IconButton(
                            onClick = { onValueChange("") }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "")}
                    }
                }
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline)
    }

}

@Composable
fun ButtonNav(
    tittle: String,
    clickNav: () -> Unit,
    modifier: Modifier,
    color: ButtonColors = ButtonDefaults.buttonColors(),
) {
    ElevatedButton(
        onClick = { clickNav() },
        colors = color,
        shape = CircleShape,
        elevation = ButtonDefaults.buttonElevation(1.dp),
        modifier = modifier
    ) {
        Text(
            text = tittle,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Loader(
    modifier: Modifier = Modifier,
) {
    val circleColors: List<Color> = listOf(
        Color(0xFF98FB98),
        Color(0xFF32CD32),
        Color(0xFF228B22),
        Color(0xFF50C878),
        Color(0xFF6B8E23)
    )
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotateAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 360,
                easing = LinearEasing
            )
        ), label = ""
    )

    CircularProgressIndicator(
        progress = { 1f },
        modifier = modifier
            .size(size = 100.dp)
            .rotate(degrees = rotateAnimation)
            .border(
                width = 4.dp,
                brush = Brush.sweepGradient(circleColors),
                shape = CircleShape
            ),
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = 1.dp,
    )
}

@Composable
fun GeneralLoader(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Loader()
    }
}

@Composable
fun NotInternetLoader(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.not_internet),
            modifier = Modifier
                .fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchPreview() {
    RickMortyTheme(darkTheme = false) {
        var value by remember {
            mutableStateOf("")
        }
        Scaffold(
            topBar ={
                CustomSearchBar(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    value = value,
                    onValueChange = {
                        value = it
                    },
                    placeHolder = {
                        Text(text = "Search Characters")
                    },
                    navigateUp = { /*TODO*/ })
            }
        ) {
        }
    }
}