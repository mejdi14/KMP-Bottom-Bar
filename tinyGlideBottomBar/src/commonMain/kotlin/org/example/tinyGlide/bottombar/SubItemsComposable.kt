package org.example.tinyGlide.bottombar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.core.bottombar.BottomBarItem
import org.example.tinyGlide.data.TinyGlideItem
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SubItemsComposable(
    modifier: Modifier,
    selectedItem: MutableState<TinyGlideItem?>,
    selectedIndex: MutableState<Int?>,
    lazyListState: LazyListState,
    hoverExitJob: MutableState<Job?>,
    isHovering: MutableState<Boolean>,
    scope: CoroutineScope,
    onIconClick: (BottomBarItem) -> Unit
) {
        selectedItem.value?.let { currentItem ->
            val density = LocalDensity.current.density
            Column (
                modifier = Modifier
                    .offset(
                        x = ((currentItem.itemCoordinatesOffset?.x
                            ?: 0f) / density).dp
                                - (((currentItem.itemSeparationSpace * (currentItem.subTinyGlideItems.size * 2))
                                + (currentItem.size * (currentItem.subTinyGlideItems.size))) / 2)
                                + ((currentItem.size - (currentItem.itemSeparationSpace)) / 2),
                        y = -(currentItem.size)
                    )
                    .hoverEffect { onHover ->
                        isHovering.value = onHover
                        if (onHover) {
                            hoverExitJob.value?.cancel()
                            hoverExitJob.value = null
                        } else {
                            hoverExitJob.value = scope.launch {
                                delay(currentItem.hoverCancelDurationMillis)
                                if (!isHovering.value) {
                                    selectedItem.value = null
                                    currentItem.parentItemDynamicSize.value = currentItem.size
                                }
                            }
                        }
                    }
            ){
                LazyRow(
                    state = lazyListState,
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Bottom,
                    modifier = modifier
                        .offset (
                            y =  - currentItem.parentAndSubVerticalSeparationSpace
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
                                    color = currentItem.backgroundColor,
                                    shape = currentItem.itemShape
                                )
                        ) {
                            Icon(
                                painter = painterResource(item.icon.selectedIconDrawable),
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



}