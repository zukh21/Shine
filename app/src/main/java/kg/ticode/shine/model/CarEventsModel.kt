package kg.ticode.shine.model

import androidx.compose.ui.graphics.vector.ImageVector
import kg.ticode.shine.enums.CarEvents

data class CarEventsModel(
    val icon: ImageVector,
    val carEvents: CarEvents
)
