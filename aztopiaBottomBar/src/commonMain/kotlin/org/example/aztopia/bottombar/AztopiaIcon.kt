package org.example.aztopia.bottombar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import org.example.aztopia.data.AztopiaItem
import org.example.aztopia.data.isSelectedItem
import org.example.aztopia.listeners.AztopiaActionListener
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun AztopiaIcon(
    item: AztopiaItem,
    selectedItem: MutableState<AztopiaItem?>,
    aztopiaActionListener: AztopiaActionListener,
) {
    Box(Modifier
        .clickable {
            aztopiaActionListener.onItemClickListener(item, item.index)
        }) {
        when (item.isSelectedItem(selectedItem.value)) {
            true -> {
                Icon(
                    painter = painterResource(item.icon.selectedIconDrawable),
                    contentDescription = item.icon.contentDescription,
                    tint = item.icon.selectedIconTint,
                    modifier = item.icon.modifier
                )
            }

            false -> {
                Icon(
                    painter = painterResource(item.icon.iconDrawable),
                    contentDescription = item.icon.contentDescription,
                    tint = item.icon.iconTintColor,
                    modifier = item.icon.modifier
                )
            }
        }
    }
}