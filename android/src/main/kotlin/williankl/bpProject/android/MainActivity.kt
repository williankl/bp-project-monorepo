package williankl.bpProject.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import williankl.bpProject.common.features.authentication.AuthenticationScreen
import williankl.bpProject.common.platform.design.core.theme.BeautifulThemeContent
import williankl.bpProject.common.platform.stateHandler.UIState
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

internal class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeautifulThemeContent {
                Navigator(
                    screen = AuthenticationScreen,
                    onBackPressed = { currentScreen ->
                        if (currentScreen is BeautifulScreen && currentScreen.screenState is UIState.Error) {
                            currentScreen.screenState = UIState.Content
                            false
                        } else true
                    }
                ) {
                    SlideTransition(it)
                }
            }
        }
    }
}
