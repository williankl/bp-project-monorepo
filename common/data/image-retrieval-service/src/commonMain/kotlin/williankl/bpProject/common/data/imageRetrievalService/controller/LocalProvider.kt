package williankl.bpProject.common.data.imageRetrievalService.controller

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

public val LocalImageRetrievalController: ProvidableCompositionLocal<ImageRetrievalController?> =
    staticCompositionLocalOf { null }
