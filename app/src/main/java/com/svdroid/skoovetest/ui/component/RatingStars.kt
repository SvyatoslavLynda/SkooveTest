package com.svdroid.skoovetest.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.svdroid.skoovetest.R

/**
 * Rating stars Component to show audio rating with filled stars
 */
@Composable
fun RatingStars(modifier: Modifier,
                rating: Int,
                ratingMax: Int = 5,
                starSize: Int = 24,
                onStarClicked: ((index: Int) -> Unit)? = null) {
    Row(modifier = modifier.testTag("RatingStars")) {
        repeat(ratingMax) { index ->
            Icon(
                Icons.Filled.Star,
                tint = if (index >= rating) Color.White else Color.Black,
                contentDescription = stringResource(id = R.string.contentDescription_audio_rating_start),
                modifier = Modifier.size(starSize.dp).testTag("star")
                     .padding(horizontal = 1.dp).clickable(
                        enabled = onStarClicked != null,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = if (onStarClicked == null) null else rememberRipple(bounded = false),
                    ) {
                        onStarClicked?.invoke(index + 1)
                    }
            )
        }
    }
}