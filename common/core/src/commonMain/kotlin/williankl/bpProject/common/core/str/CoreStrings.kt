package williankl.bpProject.common.core.str

import williankl.bpProject.common.core.models.Season

internal data class CoreStrings(
    val seasonLabel: (Season) -> String,
)
