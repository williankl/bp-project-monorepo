package williankl.bpProject.common.platform.stateHandler.bpScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen

public abstract class BeautifulScreen : Screen {

    @Composable
    override fun Content() {
        ActualScreenContent()
    }

    @Composable
    public abstract fun BeautifulContent()

    @Composable
    private fun ActualScreenContent() {
        BeautifulContent()
    }
}
