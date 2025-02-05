package williankl.bpProject.common.features.dashboard.str

internal data class DashboardStrings(
    val projectName: String,
    val profileStrings: ProfileStrings,
    val homeStrings: HomeStrings,
) {
    internal data class HomeStrings(
        val nearestLabel: String,
        val recentLabel: String,
    )

    internal data class ProfileStrings(
        val favouritesLabel: String,
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
