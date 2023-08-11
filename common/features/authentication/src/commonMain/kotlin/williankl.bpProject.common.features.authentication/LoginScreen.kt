package williankl.bpProject.common.features.authentication

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.features.authentication.models.AuthenticationFlow
import williankl.bpProject.common.features.authentication.models.SocialLoginProvider
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonType
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.input.Input
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.design.core.themedLogoResource
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

public object LoginScreen : BeautifulScreen() {

    @Composable
    override fun BeautifulContent() {
        LoginScreenContent(
            onLoginRequested = { login, password -> /* todo - handle action */ },
            onSignupRequested = { userName, login, password -> /* todo - handle action */ },
            onForgotPasswordClicked = { /* todo - handle action */ },
            onSocialLoginClicked = { /* todo - handle action */ },
            modifier = Modifier.fillMaxSize(),
        )
    }

    @Composable
    private fun LoginScreenContent(
        onLoginRequested: (String, String) -> Unit,
        onSignupRequested: (String, String, String) -> Unit,
        onForgotPasswordClicked: () -> Unit,
        onSocialLoginClicked: (SocialLoginProvider) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        var authFlow by remember {
            mutableStateOf(AuthenticationFlow.Login)
        }

        Box(
            modifier = modifier
                .background(BeautifulColor.Background.composeColor)
        ) {
            Image(
                painter = painterResource(SharedAuthenticationResources.images.login_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(500.dp)
            )

            Column(
                modifier = Modifier
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f)
                ) {
                    Image(
                        painter = painterResource(themedLogoResource()),
                        contentDescription = null,
                        modifier = Modifier.size(126.dp)
                    )
                }

                LoginOptions(
                    authenticationFlow = authFlow,
                    onLoginRequested = onLoginRequested,
                    onSignupRequested = onSignupRequested,
                    onForgotPasswordClicked = onForgotPasswordClicked,
                    onSocialLoginClicked = onSocialLoginClicked,
                    onSignupClicked = {
                        authFlow = when (authFlow) {
                            AuthenticationFlow.Signup -> AuthenticationFlow.Login
                            AuthenticationFlow.Login -> AuthenticationFlow.Signup
                        }
                    },
                    modifier = Modifier.weight(6f),
                )
            }
        }
    }

    @Composable
    private fun LoginOptions(
        authenticationFlow: AuthenticationFlow,
        onLoginRequested: (String, String) -> Unit,
        onSignupRequested: (String, String, String) -> Unit,
        onForgotPasswordClicked: () -> Unit,
        onSocialLoginClicked: (SocialLoginProvider) -> Unit,
        onSignupClicked: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            InputLoginOptions(
                authenticationFlow = authenticationFlow,
                onLoginRequested = onLoginRequested,
                onSignupRequested = onSignupRequested,
                onForgotPasswordClicked = onForgotPasswordClicked,
                modifier = Modifier,
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            AnimatedVisibility(
                visible = authenticationFlow == AuthenticationFlow.Login
            ) {
                SocialLoginOptions(
                    onSocialLoginClicked = onSocialLoginClicked,
                    modifier = Modifier,
                )
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            AccountCreationOption(
                authenticationFlow = authenticationFlow,
                onSignupClicked = onSignupClicked,
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }

    @Composable
    private fun InputLoginOptions(
        authenticationFlow: AuthenticationFlow,
        onLoginRequested: (String, String) -> Unit,
        onSignupRequested: (String, String, String) -> Unit,
        onForgotPasswordClicked: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val strings = LocalAuthenticationStrings.current

        var userName by remember {
            mutableStateOf("")
        }

        var loginText by remember {
            mutableStateOf("")
        }

        var passwordText by remember {
            mutableStateOf("")
        }

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedVisibility(
                visible = authenticationFlow == AuthenticationFlow.Signup
            ) {
                Input(
                    text = userName,
                    hint = strings.userNameHint,
                    onTextChange = { userName = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                    ),
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth(),
                )
            }

            Input(
                text = loginText,
                hint = strings.loginHint,
                onTextChange = { loginText = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                ),
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth(),
            )

            Input(
                text = passwordText,
                hint = strings.passwordHint,
                onTextChange = { passwordText = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                ),
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth(),
            )

            AnimatedVisibility(
                visible = authenticationFlow == AuthenticationFlow.Login
            ) {
                Text(
                    text = strings.forgotPasswordLabel,
                    size = TextSize.XSmall,
                    color = BeautifulColor.Secondary,
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .clickableIcon { onForgotPasswordClicked() }
                )
            }

            Button(
                label = strings.loginActionLabel,
                variant = ButtonVariant.Primary,
                onClick = {
                    when (authenticationFlow) {
                        AuthenticationFlow.Signup -> onSignupRequested(userName, loginText, passwordText)
                        AuthenticationFlow.Login -> onLoginRequested(loginText, passwordText)
                    }
                },
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth(),
            )
        }
    }

    @Composable
    private fun SocialLoginOptions(
        onSocialLoginClicked: (SocialLoginProvider) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(
                modifier = Modifier.weight(1f)
            )

            SocialLoginProvider
                .entries
                .forEach { provider ->
                    Image(
                        painter = painterResource(provider.resource),
                        contentDescription = null,
                        modifier = Modifier
                            .clickableIcon { onSocialLoginClicked(provider) }
                            .size(40.dp)
                    )
                }

            Spacer(
                modifier = Modifier.weight(1f)
            )
        }
    }

    @Composable
    @OptIn(ExperimentalAnimationApi::class)
    private fun AccountCreationOption(
        authenticationFlow: AuthenticationFlow,
        onSignupClicked: () -> Unit,
        modifier: Modifier
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
                    variant = ButtonVariant.Secondary,
                    type = ButtonType.Pill,
                    onClick = onSignupClicked
                )
            }
        }
    }
}
