package com.gm.ai.guidebook.ui.screen.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.model.GuideDetails
import com.gm.ai.guidebook.ui.components.BackButton
import com.gm.ai.guidebook.ui.components.GuideIconButton
import com.gm.ai.guidebook.ui.theme.GuideBookTheme
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
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
    var imageLoaded by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(GuideTheme.palette.background)
            .verticalScroll(scrollState),
        constraintSet = detailsScreenDecoupledConstraints(),
    ) {
        BackButton(
            modifier = Modifier.layoutId(BACK_BUTTON),
            onClick = { onEvent(DetailsEvent.BackEvent) },
        )
        Text(
            modifier = Modifier.layoutId(DETAILS_TITLE),
            text = stringResource(R.string.details),
            style = GuideTheme.typography.titleSmall,
            color = GuideTheme.palette.onBackground,
        )
        Box(
            modifier = Modifier.layoutId(DETAILS_IMAGE),
            contentAlignment = Alignment.Center
        ) {
            if (!imageLoaded) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.progress_indicator_size)),
                    color = GuideTheme.palette.primary,
                )
            }
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(GuideTheme.shape.huge),
                onState = {
                    imageLoaded = it is AsyncImagePainter.State.Success
                },
                contentScale = ContentScale.Crop,
                model = state.guide.imageUrl,
                contentDescription = null,
            )
        }
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
            maxLines = MAX_AUTHOR_TEXT_LINES,
            overflow = TextOverflow.Ellipsis,
        )
        Divider(
            modifier = Modifier
                .padding(vertical = GuideTheme.offset.large)
                .fillMaxWidth()
                .layoutId(TOPIC_DIVIDER),
            color = onBackgroundColor.copy(alpha = DIVIDER_ALPHA)
        )
        Text(
            modifier = Modifier
                .padding(bottom = GuideTheme.offset.gigantic)
                .layoutId(DESCRIPTION_TOPIC_TEXT),
            text = state.guide.description,
            style = GuideTheme.typography.bodyMedium,
            color = GuideTheme.palette.onBackground,
        )
        AnimatedVisibility(
            modifier = Modifier.layoutId(STEPS_BUTTON),
            visible = state.stepsButtonVisible,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth * 2 },
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth * 2 },
            ),
        ) {
            FloatingActionButton(
                onClick = {
                    onEvent(DetailsEvent.OpenSteps)
                },
                containerColor = GuideTheme.palette.primary,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = GuideTheme.palette.background,
                )
            }
        }
    }
}

@Composable
private fun detailsScreenDecoupledConstraints(): ConstraintSet {
    val backButtonSize = dimensionResource(R.dimen.back_button_size)
    val authorIconSize = dimensionResource(R.dimen.author_icon_size)
    val detailsImageSize = dimensionResource(R.dimen.details_image_height)

    val tinyOffset = GuideTheme.offset.tiny
    val mediumOffset = GuideTheme.offset.medium
    val regularOffset = GuideTheme.offset.regular
    val largeOffset = GuideTheme.offset.large
    val giganticOffset = GuideTheme.offset.gigantic

    return ConstraintSet {
        val backButton = createRefFor(BACK_BUTTON)
        val detailsTitle = createRefFor(DETAILS_TITLE)
        val detailsImage = createRefFor(DETAILS_IMAGE)
        val authorIcon = createRefFor(AUTHOR_ICON)
        val authorText = createRefFor(AUTHOR_TEXT)
        val likeButton = createRefFor(LIKE_BUTTON)
        val topicTitleText = createRefFor(TOPIC_TITLE_TEXT)
        val topicDivider = createRefFor(TOPIC_DIVIDER)
        val descriptionTopicText = createRefFor(DESCRIPTION_TOPIC_TEXT)
        val stepsButton = createRefFor(STEPS_BUTTON)

        constrain(backButton) {
            top.linkTo(anchor = parent.top, margin = mediumOffset)
            start.linkTo(anchor = parent.start, margin = tinyOffset)
            width = Dimension.value(backButtonSize)
            height = Dimension.value(backButtonSize)
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
            height = Dimension.value(detailsImageSize)
        }

        constrain(authorIcon) {
            top.linkTo(anchor = detailsImage.bottom, margin = largeOffset)
            start.linkTo(anchor = parent.start, margin = regularOffset)
            width = Dimension.value(authorIconSize)
            height = Dimension.value(authorIconSize)
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

        constrain(stepsButton) {
            bottom.linkTo(anchor = parent.bottom, margin = giganticOffset)
            end.linkTo(anchor = parent.end, margin = regularOffset)
        }
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
private const val STEPS_BUTTON = "steps_button"

private const val MAX_AUTHOR_TEXT_LINES = 1
private const val DIVIDER_ALPHA = 0.5f
