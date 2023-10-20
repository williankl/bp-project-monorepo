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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.features.authentication.components.AccountCreationOption
import williankl.bpProject.common.features.authentication.models.SocialLoginProvider
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.input.Input
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.design.core.themedLogoResource
import williankl.bpProject.common.platform.stateHandler.LocalRouter
import williankl.bpProject.common.platform.stateHandler.collectData
import williankl.bpProject.common.platform.stateHandler.navigation.models.Authentication.Login.AuthenticationFlow
import williankl.bpProject.common.platform.stateHandler.navigation.models.NavigationDestination
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

public class AuthenticationScreen(
    private val startingFlow: AuthenticationFlow = AuthenticationFlow.Login
) : BeautifulScreen() {

    override val toolbarConfig: ToolbarConfig
        @Composable get() = remember { ToolbarConfig() }

    @Composable
    override fun BeautifulContent() {
        val runnerModel = rememberScreenModel<AuthenticationRunnerModel>()
        val data by runnerModel.collectData()
        val router = LocalRouter.currentOrThrow

        LoginScreenContent(
            onLoginRequested = { login, password ->
                runnerModel.logIn(login, password) {
                    router.replaceAll(NavigationDestination.Dashboard)
                }
            },
            onSignupRequested = { login, userName, password ->
                runnerModel.signUp(userName, login, password) {
                    router.replaceAll(NavigationDestination.Dashboard)
                }
            },
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
            mutableStateOf(startingFlow)
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
    @OptIn(ExperimentalAnimationApi::class)
    private fun InputLoginOptions(
        authenticationFlow: AuthenticationFlow,
        onLoginRequested: (String, String) -> Unit,
        onSignupRequested: (String, String, String) -> Unit,
        onForgotPasswordClicked: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val focusManager = LocalFocusManager.current
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

        var isShowingPassword by remember {
            mutableStateOf(false)
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
                    maxLines = 1,
                    onTextChange = { userName = it },
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth(),
                )
            }

            Input(
                text = loginText,
                hint = strings.loginHint,
                maxLines = 1,
                onTextChange = { loginText = it },
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth(),
            )

            Input(
                text = passwordText,
                hint = strings.passwordHint,
                maxLines = 1,
                sideContentsOnExtremes = true,
                onTextChange = { passwordText = it },
                visualTransformation =
                if (isShowingPassword) VisualTransformation.None
                else PasswordVisualTransformation('•'),
                keyboardActions = KeyboardActions(
                    onDone = {
                        when (authenticationFlow) {
                            AuthenticationFlow.Signup -> onSignupRequested(userName, loginText, passwordText)
                            AuthenticationFlow.Login -> onLoginRequested(loginText, passwordText)
                        }
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                endContent = {
                    val iconResource = if (isShowingPassword) {
                        SharedDesignCoreResources.images.ic_eye_off
                    } else {
                        SharedDesignCoreResources.images.ic_eye
                    }

                    Image(
                        painter = painterResource(iconResource),
                        contentDescription = null,
                        modifier = Modifier
                            .clickableIcon(0.dp) {
                                isShowingPassword = isShowingPassword.not()
                            }
                            .size(24.dp)
                    )
                },
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

            AnimatedContent(
                targetState = authenticationFlow
            ) { flow ->
                Button(
                    label = when (flow) {
                        AuthenticationFlow.Signup -> strings.signupActionLabel
                        AuthenticationFlow.Login -> strings.loginActionLabel
                    },
                    variant = ButtonVariant.Primary,
                    onClick = {
                        when (flow) {
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
    }

    @Composable
    private fun SocialLoginOptions(
        onSocialLoginClicked: (SocialLoginProvider) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(40.dp),
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
}
