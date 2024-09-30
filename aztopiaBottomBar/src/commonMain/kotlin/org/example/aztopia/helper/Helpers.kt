package org.example.aztopia.helper

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.example.aztopia.data.AztopiaItem
import org.example.aztopia.data.isSelectedItem

internal fun handleHoverAction(
    isHovering: MutableState<Boolean>,
    onHover: Boolean,
    item: AztopiaItem,
    selectedItem: MutableState<AztopiaItem?>,
    hoverExitJob: MutableState<Job?>,
    scope: CoroutineScope
) {
    isHovering.value = onHover
    item.hoverActionListener.onHoverEnter(item)
    if (onHover) {
        if (item.isSelectedItem(selectedItem.value)) {
            hoverExitJob.value?.cancel()
            hoverExitJob.value = null
        }
        selectedItem.value = item
        item.hoverActionListener.onHoverParentItem(item)
        item.parentItemDynamicSize.value =
            if (!item.isSelectedItem(selectedItem.value)) item.size else
                item.size * item.onSelectItemSizeChangeFriction
    } else {
        hoverExitJob.value = scope.launch {
            selectedItem.value = null
            item.parentItemDynamicSize.value = item.size
            item.hoverActionListener.onHoverExit(item)
        }
    }
}