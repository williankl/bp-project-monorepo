package williankl.bpProject.common.features.authentication.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import williankl.bpProject.common.features.authentication.LocalAuthenticationStrings
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonType
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.stateHandler.navigation.models.Authentication
import williankl.bpProject.common.platform.stateHandler.navigation.models.Authentication.Login.AuthenticationFlow

@Composable
@OptIn(ExperimentalAnimationApi::class)
internal fun AccountCreationOption(
    authenticationFlow: AuthenticationFlow,
    onSignupClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val strings = LocalAuthenticationStrings.current
    AnimatedContent(
        targetState = authenticationFlow
    ) { flow ->
        val (message, actionLabel) = when (flow) {
            AuthenticationFlow.Signup -> strings.alreadyHasAccountLabel to strings.logInLabel
            AuthenticationFlow.Login -> strings.hasNoAccountLabel to strings.signUpLabel
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = modifier,
        ) {
            Text(
                text = message,
                size = TextSize.XSmall,
                color = BeautifulColor.Secondary,
                modifier = Modifier
            )

            Button(
                label = actionLabel,
                variant = ButtonVariant.PrimaryGhost,
                type = ButtonType.Pill,
                onClick = onSignupClicked
            )
        }
    }
}