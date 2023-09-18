package williankl.bpProject.common.features.dashboard.pages.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen

internal object HomePage : BeautifulScreen() {

    @Composable
    override fun BeautifulContent() {
        Box(Modifier.fillMaxSize())
    }

}