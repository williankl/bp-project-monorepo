package williankl.bpProject.common.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import williankl.bpProject.common.core.LocalCoreStrings
import williankl.bpProject.common.platform.design.core.ComposeString

@Serializable
public enum class Season(
    public val label: ComposeString,
) {
    @SerialName("summer")
    Summer(
        label = { LocalCoreStrings.current.seasonLabel(Summer) }
    ),
    @SerialName("autumn")
    Autumn(
        label = { LocalCoreStrings.current.seasonLabel(Autumn) }
    ),
    @SerialName("winter")
    Winter(
        label = { LocalCoreStrings.current.seasonLabel(Winter) }
    ),
    @SerialName("spring")
    Spring(
        label = { LocalCoreStrings.current.seasonLabel(Spring) }
    ),
}
