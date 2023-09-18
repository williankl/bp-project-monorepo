package williankl.bpProject.common.features.dashboard.str

internal data class DashboardStrings(
    val projectName: String,
    val profileStrings: ProfileStrings,
) {
    internal data class ProfileStrings(
        val menuStrings: MenuStrings,
    ) {
        internal data class MenuStrings(
            val editLabel: String,
            val settingsLabel: String,
            val supportLabel: String,
            val exitLabel: String,
        )
    }
}
