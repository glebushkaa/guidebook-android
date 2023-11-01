package com.gm.ai.guidebook.ui.screen.steps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.model.Step
import com.gm.ai.guidebook.ui.components.GuideIconButton
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/31/2023
 */

@Preview
@Composable
private fun StepsScreenPreview() {
    val state = StepsState(
        steps = listOf(
            Step(
                title = "Step 1",
                description = "Description 1",
                imageUrl = "https://i.imgur.com/7bMqysJ.jpg",
                id = "2",
                order = 1,
                guideId = ""
            ),
            Step(
                title = "Step 1",
                description = "Description 1",
                imageUrl = "https://i.imgur.com/7bMqysJ.jpg",
                id = "2",
                order = 1,
                guideId = ""
            ),
            Step(
                title = "Step 1",
                description = "Description 1",
                imageUrl = "https://i.imgur.com/7bMqysJ.jpg",
                id = "2",
                order = 1,
                guideId = ""
            ),
        )
    )
    StepsScreen(state = state)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StepsScreen(
    state: StepsState
) {
    val pagerState = rememberPagerState(pageCount = { state.steps.size })
    var currentPage by rememberSaveable { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(bottom = GuideTheme.offset.superGigantic)
                .fillMaxSize()
        ) { page ->
            StepContent(state.steps[page])
        }

        PagerControllerSection(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = GuideTheme.offset.regular),
            forwardVisible = pagerState.canScrollForward,
            backwardVisible = pagerState.canScrollBackward,
            onForwardClick = {
                currentPage = pagerState.currentPage + 1
            },
            onBackClick = {
                currentPage = pagerState.currentPage - 1
            },
            currentPage = pagerState.currentPage + 1,
            totalPageCount = pagerState.pageCount
        )
    }

    LaunchedEffect(key1 = currentPage) {
        if (currentPage == pagerState.currentPage) return@LaunchedEffect
        pagerState.animateScrollToPage(currentPage)
    }
}

@Composable
private fun PagerControllerSection(
    modifier: Modifier = Modifier,
    forwardVisible: Boolean,
    backwardVisible: Boolean,
    onForwardClick: () -> Unit,
    onBackClick: () -> Unit,
    currentPage: Int = 0,
    totalPageCount: Int = 0,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                dimensionResource(R.dimen.pager_controller_section_height)
            )
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .padding(start = GuideTheme.offset.huge)
                .align(Alignment.CenterStart),
            visible = backwardVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            GuideIconButton(
                modifier = Modifier.rotate(BACKWARD_ICON_ROTATE_ANGLE),
                iconResId = R.drawable.ic_arrow_forward,
                tint = GuideTheme.palette.onBackground,
                onClick = onBackClick
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "${currentPage}/${totalPageCount}",
            style = GuideTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            color = GuideTheme.palette.onBackground,
        )
        AnimatedVisibility(
            modifier = Modifier
                .padding(end = GuideTheme.offset.huge)
                .align(Alignment.CenterEnd),
            visible = forwardVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            GuideIconButton(
                iconResId = R.drawable.ic_arrow_forward,
                tint = GuideTheme.palette.onBackground,
                onClick = onForwardClick
            )
        }
    }
}

@Composable
private fun StepContent(step: Step) {
    val scrollState = rememberScrollState()
    var imageLoaded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GuideTheme.palette.background)
            .verticalScroll(scrollState)
    ) {

        Box(
            modifier = Modifier
                .padding(
                    top = GuideTheme.offset.large,
                    start = GuideTheme.offset.huge,
                    end = GuideTheme.offset.huge
                )
                .fillMaxWidth()
                .height(
                    dimensionResource(R.dimen.step_image_height)
                ),
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
                model = step.imageUrl,
                contentDescription = step.title,
            )
        }
        Text(
            modifier = Modifier.padding(
                top = GuideTheme.offset.large,
                start = GuideTheme.offset.huge,
                end = GuideTheme.offset.huge
            ),
            text = step.title,
            style = GuideTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            color = GuideTheme.palette.onBackground
        )
        Text(
            modifier = Modifier.padding(
                top = GuideTheme.offset.large,
                start = GuideTheme.offset.huge,
                end = GuideTheme.offset.huge
            ),
            text = step.description,
            style = GuideTheme.typography.bodyMedium,
            color = GuideTheme.palette.onBackground
        )
    }
}

private const val BACKWARD_ICON_ROTATE_ANGLE = 180f