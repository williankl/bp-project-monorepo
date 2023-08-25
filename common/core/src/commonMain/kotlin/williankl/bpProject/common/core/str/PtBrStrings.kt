package williankl.bpProject.common.core.str

import cafe.adriel.lyricist.LyricistStrings
import williankl.bpProject.common.core.models.Season

@LyricistStrings(languageTag = "pt-BR", default = true)
internal val ptBrStrings = CoreStrings(
    seasonLabel = { season ->
        when (season) {
            Season.Summer -> "Verão"
            Season.Autumn -> "Outono"
            Season.Winter -> "Inverno"
            Season.Spring -> "Primavera"
        }
    }
)
