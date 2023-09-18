package williankl.bpProject.common.features.dashboard.str

import cafe.adriel.lyricist.LyricistStrings
import williankl.bpProject.common.features.dashboard.str.DashboardStrings.ProfileStrings
import williankl.bpProject.common.features.dashboard.str.DashboardStrings.ProfileStrings.MenuStrings

@LyricistStrings(languageTag = "pt-BR", default = true)
internal val ptBrStrings = DashboardStrings(
    projectName = "Beautiful Places",
    profileStrings = ProfileStrings(
        menuStrings = MenuStrings(
            editLabel = "Editar",
            settingsLabel = "Configurações",
            supportLabel = "Suporte",
            exitLabel = "Sair",
        )
    )
)
