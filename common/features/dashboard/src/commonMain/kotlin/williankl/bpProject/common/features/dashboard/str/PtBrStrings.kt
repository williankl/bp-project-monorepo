package williankl.bpProject.common.features.dashboard.str

import cafe.adriel.lyricist.LyricistStrings
import williankl.bpProject.common.features.dashboard.str.DashboardStrings.ProfileStrings
import williankl.bpProject.common.features.dashboard.str.DashboardStrings.ProfileStrings.MenuStrings

@LyricistStrings(languageTag = "pt-BR", default = true)
internal val ptBrStrings = DashboardStrings(
    projectName = "Beautiful Places",
    homeStrings = DashboardStrings.HomeStrings(
        distanceLabel = { distanceInMeters ->
            if (distanceInMeters > 1000) {
                "${(distanceInMeters / 1000)}km"
            } else {
                "${distanceInMeters}m"
            }
        },
        nearestLabel = "Perto de você",
        recentLabel = "Rencentes",
    ),
    profileStrings = ProfileStrings(
        menuStrings = MenuStrings(
            editLabel = "Editar",
            settingsLabel = "Configurações",
            supportLabel = "Suporte",
            exitLabel = "Sair",
        )
    )
)
