package williankl.bpProject.common.platform.design.core.shapes

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

public sealed class BeautifulShape(
    public val composeShape: Shape,
) {
    public sealed class Rounded(shape: Shape) : BeautifulShape(shape) {
        public data object Regular : Rounded(
            shape = RoundedCornerShape(8.dp)
        )

        public data object Large : Rounded(
            shape = RoundedCornerShape(16.dp)
        )

        public data object Circle : Rounded(
            shape = CircleShape
        )
    }
}
