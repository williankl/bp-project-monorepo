package williankl.bpProject.common.core.models

import williankl.bpProject.common.core.LocalCoreStrings
import williankl.bpProject.common.platform.design.core.ComposeString

public enum class Season(
    public val label: ComposeString,
) {
    Summer(
        label = { LocalCoreStrings.current.seasonLabel(Summer) }
    ),
    Autumn(
        label = { LocalCoreStrings.current.seasonLabel(Autumn) }
    ),
    Winter(
        label = { LocalCoreStrings.current.seasonLabel(Winter) }
    ),
    Spring(
        label = { LocalCoreStrings.current.seasonLabel(Spring) }
    ),
}
