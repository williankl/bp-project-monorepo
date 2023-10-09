package williankl.bpProject.common.platform.design.components.str

import cafe.adriel.lyricist.LyricistStrings
import williankl.bpProject.common.platform.design.components.str.DesignComponentsStrings.*
import kotlin.time.Duration.Companion.milliseconds

@LyricistStrings(languageTag = "pt-BR", default = true)
internal val ptBrStrings = DesignComponentsStrings(
    bubbleStrings = CommentBubbleStrings(
        timePassed = { timeInMillis ->
            val timeSince = timeInMillis.milliseconds

            when {
                timeSince.inWholeMinutes <= 0 -> "Moments ago"
                timeSince.inWholeMinutes <= 59 -> "Há ${timeSince.inWholeMinutes} minuto(s)"
                timeSince.inWholeHours <= 23 -> "Há ${timeSince.inWholeHours} hora(s)"
                else -> "Há ${timeSince.inWholeDays} dia(s)"
            }
        }
    )
)
