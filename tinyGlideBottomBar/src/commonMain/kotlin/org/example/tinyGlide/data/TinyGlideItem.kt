package org.example.tinyGlide.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.core.bottombar.BottomBarIdentifier
import org.example.core.bottombar.BottomBarItem
import org.jetbrains.compose.resources.DrawableResource
import kotlin.time.Duration

data class TinyGlideItem(
    override val identifier: BottomBarIdentifier,
    override val icon: DrawableResource,
    override val contentDescription: String,
    override val size: Dp = 50.dp,
    override val title: String = "options",
    val radius: Dp = 10.dp,
    val backgroundColor: Color = Color.Blue,
    val onSelectItemSizeChangeFriction : Float = 1.2f,
    val onSelectItemSizeChangeDurationMillis: Int = 300,
    val itemSeparationSpace: Dp = 10.dp,

) : BottomBarItem()