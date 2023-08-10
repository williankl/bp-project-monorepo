package williankl.bpProject.common.platform.design.core.text

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

public enum class TextSize(
    public val textUnit: TextUnit,
) {
    XSmall(10.sp),
    Small(12.sp),
    Regular(14.sp),
    Large(16.sp),
    XLarge(18.sp),
    XXLarge(20.sp),
}
