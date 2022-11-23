package com.tmhwrd.weather

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun IconImage(id: String) = AsyncImage(
    model = ImageRequest.Builder(LocalContext.current)
        .data("https://developer.accuweather.com/sites/default/files/${id}-s.png")
        .crossfade(true).build(),
    contentDescription = null,
    modifier = Modifier.requiredSize(72.dp),
    contentScale = ContentScale.Fit
)
