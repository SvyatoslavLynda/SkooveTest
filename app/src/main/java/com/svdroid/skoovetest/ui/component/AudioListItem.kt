package com.svdroid.skoovetest.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.svdroid.skoovetest.R
import com.svdroid.skoovetest.data.ui.SongUIModel

/**
 * Audio item for the overview list
 */
@Composable
fun AudioListItem(
    audio: SongUIModel,
    onFavoriteClicked: (newState: Boolean) -> Unit,
    onItemClicked: () -> Unit
) {
    Column(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .border(1.dp, MaterialTheme.colorScheme.onBackground)
        .background(MaterialTheme.colorScheme.surface)
        .clickable {
            onItemClicked()
        }
    ) {

        Box {

            // Cover image
            AsyncImage(
                model = audio.coverUrl,
                contentDescription = stringResource(id = R.string.contentDescription_audio_cover),
//                      shimmerParams = ShimmerParams( //todo review the possibilities to use shimmer for new version of coil
//                              baseColor = MaterialTheme.colorScheme.background,
//                              highlightColor = MaterialTheme.colorScheme.surface
//                      ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(3f / 2f)
                    .fillMaxWidth()
            )

            // Rating element
            RatingStars(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Color.White.copy(alpha = 0.5f))
                    .padding(4.dp),
                audio.rating,
            )
        }

        // Title and favorite section
        Row(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .align(Alignment.End)
                .padding(horizontal = 8.dp)
                .heightIn(64.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Title
            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                textAlign = TextAlign.Center,
                text = audio.title,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Favorite Heart
            FavoriteElement(modifier = Modifier,
                favoriteState = audio.isFavorite,
                onClick = { onFavoriteClicked(it) })
        }
    }
}

