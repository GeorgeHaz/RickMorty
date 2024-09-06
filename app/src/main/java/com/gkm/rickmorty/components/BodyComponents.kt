package com.gkm.rickmorty.components

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gkm.rickmorty.R

@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    button: ImageVector,
    showButton: Boolean = false,
    onClickBackButton: () -> Unit,
    showImage:Boolean = false,
    imageApp: Painter = painterResource(id = R.drawable.rickmorty),
    showSearch: Boolean = false,
    value:String = "",
    onValueChange: (String) -> Unit = {},
    placeHolder:String = ""
) {
    val requester = remember{FocusRequester()}
    val focusManager = LocalFocusManager.current
    Column{
        Row (
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
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
            Image(
                painter = imageApp,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth(),
                alignment = Alignment.Center
            )
        }
        if(showSearch){
            TextField(
                value = value,
                onValueChange = {character ->
                    onValueChange(character)
                },
                placeholder = {
                    Text(text = placeHolder)
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester = requester)
                    .padding(horizontal = 5.dp),
                trailingIcon = {
                    if (value.isNotBlank()){
                        IconButton(onClick = { onValueChange("") }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "clear search",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(20.dp))
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                    }
                )
            )
        }
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