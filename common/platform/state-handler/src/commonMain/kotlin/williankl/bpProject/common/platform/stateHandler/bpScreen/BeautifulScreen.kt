package williankl.bpProject.common.platform.stateHandler.bpScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import williankl.bpProject.common.platform.stateHandler.UIState

public abstract class BeautifulScreen : Screen {

    @Composable
    override fun Content() {
        ActualScreenContent()
    }

    @Composable
    public abstract fun BeautifulContent()

    @Composable
    private fun ActualScreenContent(){
        BeautifulContent()
    }
}
