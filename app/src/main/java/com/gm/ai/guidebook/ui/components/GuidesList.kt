package com.gm.ai.guidebook.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import com.gm.ai.guidebook.R
import com.gm.ai.guidebook.core.android.extensions.applyIf
import com.gm.ai.guidebook.core.android.extensions.clickableWithoutRipple
import com.gm.ai.guidebook.model.Guide
import com.gm.ai.guidebook.ui.theme.GuideTheme

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@Composable
fun GuidesList(
    modifier: Modifier = Modifier,
    list: List<Guide> = emptyList(),
    guideClicked: (String) -> Unit = {},
    lazyListState: LazyListState = rememberLazyListState(),
) {
    val topItemShape = GuideTheme.shape.medium.copy(
        bottomEnd = CornerSize(0.dp),
        bottomStart = CornerSize(0.dp),
    )
    val bottomItemShape = GuideTheme.shape.medium.copy(
        topStart = CornerSize(0.dp),
        topEnd = CornerSize(0.dp),
    )

    LazyColumn(
        modifier = modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(GuideTheme.offset.min),
        state = lazyListState,
    ) {
        items(
            items = list,
            key = { guide -> guide.id },
        ) { guide ->
            GuideItem(
                modifier = Modifier
                    .applyIf(guide.id == list.first().id) {
                        clip(topItemShape)
                    }
                    .applyIf(guide.id == list.last().id) {
                        clip(bottomItemShape)
                    },
                guide = guide,
                guideClicked = guideClicked,
            )
        }
        item {
            Spacer(modifier = Modifier.height(GuideTheme.offset.large))
        }
    }
}

@Composable
private fun GuideItem(
    modifier: Modifier = Modifier,
    guide: Guide,
    guideClicked: (String) -> Unit = {},
) {
    val smallOffset = GuideTheme.offset.small
    val mediumOffset = GuideTheme.offset.medium
    val largeOffset = GuideTheme.offset.large
    val giganticOffset = GuideTheme.offset.huge
    ConstraintLayout(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(GuideTheme.palette.surface)
            .clickableWithoutRipple {
                guideClicked.invoke(guide.id)
            },
        constraintSet = guideItemDecoupledConstraints(
            smallOffset = smallOffset,
            mediumOffset = mediumOffset,
            largeOffset = largeOffset,
            giganticOffset = giganticOffset,
        ),
    ) {
        Text(
            modifier = Modifier.layoutId(GUIDE_ITEM_IMAGE),
            text = guide.emoji,
            fontSize = 40.sp,
        )
        Text(
            modifier = Modifier.layoutId(GUIDE_ITEM_TITLE),
            text = guide.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = GuideTheme.typography.titleMedium,
            color = GuideTheme.palette.onSurface,
        )

        Text(
            modifier = Modifier.layoutId(GUIDE_ITEM_DESCRIPTION),
            text = guide.description,
            style = GuideTheme.typography.bodyMedium,
            color = GuideTheme.palette.onSurface.copy(alpha = 0.8f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Icon(
            modifier = Modifier.layoutId(GUIDE_ITEM_ARROW),
            painter = painterResource(id = R.drawable.ic_arrow_next),
            tint = GuideTheme.palette.onSurface,
            contentDescription = null,
        )
    }
}

private fun guideItemDecoupledConstraints(
    smallOffset: Dp,
    mediumOffset: Dp,
    largeOffset: Dp,
    giganticOffset: Dp,
) = ConstraintSet {
    val image = createRefFor(GUIDE_ITEM_IMAGE)
    val title = createRefFor(GUIDE_ITEM_TITLE)
    val description = createRefFor(GUIDE_ITEM_DESCRIPTION)
    val arrow = createRefFor(GUIDE_ITEM_ARROW)

    constrain(image) {
        top.linkTo(anchor = parent.top, margin = giganticOffset)
        bottom.linkTo(anchor = parent.bottom, margin = giganticOffset)
        start.linkTo(anchor = parent.start, margin = mediumOffset)
        height = Dimension.value(40.dp)
        width = Dimension.value(40.dp)
    }

    constrain(title) {
        top.linkTo(anchor = parent.top, margin = mediumOffset)
        start.linkTo(anchor = image.end, margin = largeOffset)
        end.linkTo(anchor = arrow.start, margin = mediumOffset)
        width = Dimension.fillToConstraints
    }

    constrain(description) {
        top.linkTo(anchor = title.bottom, margin = smallOffset)
        start.linkTo(anchor = image.end, margin = largeOffset)
        end.linkTo(anchor = arrow.start, margin = mediumOffset)
        width = Dimension.fillToConstraints
    }

    constrain(arrow) {
        top.linkTo(anchor = parent.top)
        bottom.linkTo(anchor = parent.bottom)
        end.linkTo(anchor = parent.end, margin = smallOffset)
        height = Dimension.value(16.dp)
    }
}

private const val GUIDE_ITEM_IMAGE = "guideItemImage"
private const val GUIDE_ITEM_TITLE = "guideItemTitle"
private const val GUIDE_ITEM_DESCRIPTION = "guideItemDescription"
private const val GUIDE_ITEM_ARROW = "guideItemArrow"
