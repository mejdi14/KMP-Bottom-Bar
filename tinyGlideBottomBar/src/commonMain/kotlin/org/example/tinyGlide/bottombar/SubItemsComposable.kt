package org.example.tinyGlide.bottombar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import org.example.core.bottombar.BottomBarItem
import org.example.tinyGlide.data.TinyGlideItem
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SubItemsComposable(
    modifier: Modifier,
    selectedItem: TinyGlideItem?,
    selectedIndex: MutableState<Int?>,
    lazyListState: LazyListState,
    onIconClick: (BottomBarItem) -> Unit
) {
    selectedItem?.let { currentItem ->
        val density = LocalDensity.current.density
        Logger.i("coordinates: ${currentItem.itemCoordinatesOffset?.x}")
        Logger.i(
            "calculation: ${
                ((currentItem.itemCoordinatesOffset?.x
                    ?: 0f) / density).dp - (((currentItem.size * (currentItem.subTinyGlideItems.size))) / 2)
            }"
        )
        LazyRow(
            state = lazyListState,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom,
            modifier = modifier
                .offset(
                    x = ((currentItem.itemCoordinatesOffset?.x
                        ?: 0f) / density).dp
                            - (((currentItem.itemSeparationSpace * (currentItem.subTinyGlideItems.size * 2))
                            + (currentItem.size * (currentItem.subTinyGlideItems.size))) / 2)
                            + ((currentItem.size - (currentItem.itemSeparationSpace)) / 2),
                    y = -(currentItem.size + currentItem.parentAndSubVerticalSeparationSpace)
                )
        ) {
            itemsIndexed(currentItem.subTinyGlideItems) { index, item ->
                val parentItemDynamicSize by remember { mutableStateOf(item.size) }
                val animatedParentWidth by animateDpAsState(
                    targetValue = parentItemDynamicSize,
                    animationSpec = tween(durationMillis = item.onSelectItemSizeChangeDurationMillis)
                )
                Box(
                    Modifier.width(item.itemSeparationSpace)
                )
                IconButton(
                    onClick = {
                        selectedIndex.value = index
                        onIconClick(item)
                    },
                    modifier = Modifier.size(animatedParentWidth)

                        .background(
                            color = currentItem.backgroundColor, shape = currentItem.itemShape
                        )
                ) {
                    Icon(
                        painter = painterResource(item.selectedIconDrawable),
                        contentDescription = item.contentDescription,
                        tint = Color.White,
                        modifier = Modifier
                    )
                }
                Box(Modifier.width(item.itemSeparationSpace))
            }
        }
    }
}