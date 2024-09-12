package ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.example.core.bottombar.BottomBarItem
import org.example.tinyGlide.data.TinyGlideItem
import org.jetbrains.compose.resources.painterResource

expect fun Modifier.hoverEffect(onHover: (Boolean) -> Unit): Modifier

@Composable
fun TinyGlideBottomBar(
    bottomBarItems: List<TinyGlideItem>,
    parentModifier: Modifier,
    onIconClick: (BottomBarItem) -> Unit
) {
    val selectedIndex = remember { mutableStateOf(0) }
    val itemWidth = 50.dp

    val lazyListState = rememberLazyListState()

    val density = LocalDensity.current

    val animatedOffset = animateDpAsState(
        targetValue = (selectedIndex.value * itemWidth.value).dp
    )
    Box(
        parentModifier.fillMaxWidth().padding(5.dp)

    ) {
        LazyRow(
            state = lazyListState,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(bottomBarItems) { index, item ->
                var parentWidth by remember { mutableStateOf(50.dp) }
                Box(
                    Modifier.width(10.dp)
                )
                IconButton(
                    onClick = {
                        selectedIndex.value = index
                        onIconClick(item)
                    },
                    modifier = Modifier.size(parentWidth).align(Alignment.Center)
                        .background(
                            color = Color.Black, shape = RoundedCornerShape(10.dp)
                        )
                        .hoverEffect { onHover ->
                            parentWidth = if (onHover) 70.dp else 50.dp
                        }

                ) {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.contentDescription,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Box(Modifier.width(10.dp))
            }
        }
    }
}
