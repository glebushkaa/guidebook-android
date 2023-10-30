package com.gm.ai.guidebook.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import coil.compose.AsyncImage
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.model.GuideDetails
import com.gm.ai.guidebook.ui.components.BackButton
import com.gm.ai.guidebook.ui.components.GuideIconButton
import com.gm.ai.guidebook.ui.theme.GuideBookTheme
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 *
 * TODO : Add no guide was found state
 *
 */

@Preview
@Composable
fun DetailsScreenPreview() {
    val details = GuideDetails(
        id = "1",
        title = "C++",
        emoji = "👨‍💻",
        description = "C++ is object oriented programming language",
        imageUrl = "",
        authorId = "",
        authorName = "gle.bushkaa",
        favorite = false,
    )
    val state = DetailsState(
        guide = details,
    )
    GuideBookTheme(darkTheme = false) {
        DetailsScreen(state = state)
    }
}

@Composable
fun DetailsScreen(
    state: DetailsState,
    onEvent: (DetailsEvent) -> Unit = {},
) {
    val onBackgroundColor = GuideTheme.palette.onBackground
    val scrollState = rememberScrollState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(GuideTheme.palette.background)
            .verticalScroll(scrollState),
        constraintSet = detailsScreenDecoupledConstraints(
            tinyOffset = GuideTheme.offset.tiny,
            mediumOffset = GuideTheme.offset.medium,
            regularOffset = GuideTheme.offset.regular,
            largeOffset = GuideTheme.offset.large,
            giganticOffset = GuideTheme.offset.gigantic,
        ),
    ) {
        BackButton(
            modifier = Modifier.layoutId(BACK_BUTTON),
            onClick = { onEvent(DetailsEvent.BackEvent) },
        )
        Text(
            modifier = Modifier.layoutId(DETAILS_TITLE),
            text = "Details",
            style = GuideTheme.typography.titleSmall,
            color = GuideTheme.palette.onBackground,
        )
        AsyncImage(
            modifier = Modifier.layoutId(DETAILS_IMAGE),
            contentScale = ContentScale.Crop,
            model = state.guide.imageUrl,
            contentDescription = null,
        )
        Icon(
            modifier = Modifier
                .background(
                    color = GuideTheme.palette.onBackground,
                    shape = GuideTheme.shape.round,
                )
                .layoutId(AUTHOR_ICON),
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = null,
            tint = GuideTheme.palette.background,
        )
        Text(
            modifier = Modifier.layoutId(AUTHOR_TEXT),
            text = state.guide.authorName,
            style = GuideTheme.typography.bodyMedium,
            color = GuideTheme.palette.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        GuideIconButton(
            modifier = Modifier.layoutId(LIKE_BUTTON),
            iconResId = if (state.guide.favorite) {
                R.drawable.ic_filled_like
            } else {
                R.drawable.ic_like
            },
            onClick = { onEvent(DetailsEvent.LikeClicked) },
        )
        Text(
            modifier = Modifier.layoutId(TOPIC_TITLE_TEXT),
            text = state.guide.title,
            style = GuideTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
            color = GuideTheme.palette.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(
            modifier = Modifier
                .layoutId(TOPIC_DIVIDER)
                .height(GuideTheme.offset.gigantic)
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = onBackgroundColor.copy(alpha = 0.5f),
                        start = Offset(x = 0f, y = size.height / 2),
                        end = Offset(x = size.width, y = size.height / 2),
                    )
                },
        )
        Text(
            modifier = Modifier
                .padding(bottom = GuideTheme.offset.gigantic)
                .layoutId(DESCRIPTION_TOPIC_TEXT),
            text = state.guide.description,
            style = GuideTheme.typography.bodyMedium,
            color = GuideTheme.palette.onBackground,
        )
    }
}

private fun detailsScreenDecoupledConstraints(
    tinyOffset: Dp,
    mediumOffset: Dp,
    regularOffset: Dp,
    largeOffset: Dp,
    giganticOffset: Dp,
) = ConstraintSet {
    val backButton = createRefFor(BACK_BUTTON)
    val detailsTitle = createRefFor(DETAILS_TITLE)
    val detailsImage = createRefFor(DETAILS_IMAGE)
    val authorIcon = createRefFor(AUTHOR_ICON)
    val authorText = createRefFor(AUTHOR_TEXT)
    val likeButton = createRefFor(LIKE_BUTTON)
    val topicTitleText = createRefFor(TOPIC_TITLE_TEXT)
    val topicDivider = createRefFor(TOPIC_DIVIDER)
    val descriptionTopicText = createRefFor(DESCRIPTION_TOPIC_TEXT)

    constrain(backButton) {
        top.linkTo(anchor = parent.top, margin = mediumOffset)
        start.linkTo(anchor = parent.start, margin = tinyOffset)
        width = Dimension.value(32.dp)
        height = Dimension.value(32.dp)
    }

    constrain(detailsTitle) {
        top.linkTo(anchor = backButton.top)
        bottom.linkTo(anchor = backButton.bottom)
        start.linkTo(anchor = parent.start)
        end.linkTo(anchor = parent.end)
    }

    constrain(detailsImage) {
        top.linkTo(anchor = backButton.bottom, margin = largeOffset)
        start.linkTo(anchor = parent.start, margin = giganticOffset)
        end.linkTo(anchor = parent.end, margin = giganticOffset)
        width = Dimension.fillToConstraints
        height = Dimension.value(300.dp)
    }

    constrain(authorIcon) {
        top.linkTo(anchor = detailsImage.bottom, margin = largeOffset)
        start.linkTo(anchor = parent.start, margin = regularOffset)
        width = Dimension.value(24.dp)
        height = Dimension.value(24.dp)
    }

    constrain(authorText) {
        top.linkTo(anchor = detailsImage.bottom, margin = largeOffset)
        start.linkTo(anchor = authorIcon.end, margin = regularOffset)
        end.linkTo(anchor = likeButton.start, margin = regularOffset)
        width = Dimension.fillToConstraints
    }

    constrain(likeButton) {
        top.linkTo(anchor = authorText.top)
        bottom.linkTo(anchor = authorText.bottom)
        end.linkTo(anchor = parent.end, margin = regularOffset)
    }

    constrain(topicTitleText) {
        top.linkTo(anchor = authorText.bottom, margin = mediumOffset)
        start.linkTo(anchor = parent.start, margin = regularOffset)
        end.linkTo(anchor = parent.end, margin = tinyOffset)
        width = Dimension.fillToConstraints
    }
    constrain(topicDivider) {
        top.linkTo(anchor = topicTitleText.bottom)
        start.linkTo(anchor = parent.start, margin = regularOffset)
        end.linkTo(anchor = parent.end, margin = regularOffset)
        width = Dimension.fillToConstraints
    }
    constrain(descriptionTopicText) {
        top.linkTo(anchor = topicDivider.bottom)
        start.linkTo(anchor = parent.start, margin = regularOffset)
        end.linkTo(anchor = parent.end, margin = tinyOffset)
        width = Dimension.fillToConstraints
    }
}

private const val BACK_BUTTON = "back_button"
private const val DETAILS_TITLE = "details_title"
private const val DETAILS_IMAGE = "details_image"
private const val AUTHOR_ICON = "author_icon"
private const val AUTHOR_TEXT = "author_text"
private const val LIKE_BUTTON = "like_button"
private const val TOPIC_TITLE_TEXT = "topic_title_text"
private const val DESCRIPTION_TOPIC_TEXT = "description_topic_text"
private const val TOPIC_DIVIDER = "topic_divider"