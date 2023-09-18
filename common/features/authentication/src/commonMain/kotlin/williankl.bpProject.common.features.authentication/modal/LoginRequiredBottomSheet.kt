package williankl.bpProject.common.features.authentication.modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.features.authentication.LocalAuthenticationStrings
import williankl.bpProject.common.features.authentication.components.AccountCreationOption
import williankl.bpProject.common.features.authentication.models.SocialLoginProvider
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen
import williankl.bpProject.common.platform.stateHandler.LocalRouter
import williankl.bpProject.common.platform.stateHandler.navigation.models.Authentication
import williankl.bpProject.common.platform.stateHandler.navigation.models.Authentication.Login.AuthenticationFlow

public class LoginRequiredBottomSheet : BeautifulScreen() {

    @Composable
    override fun BeautifulContent() {
        val router = LocalRouter.currentOrThrow

        LoginRequiredContent(
            onDefaultLoginRequested = {
                router.hideBottomSheet()
                router.push(Authentication.Login())
            },
            onSignupClicked = {
                router.hideBottomSheet()
                router.push(Authentication.Login(AuthenticationFlow.Signup))
            },
            onSocialProviderLoginRequested = {
                /* todo - handle social login */
            },
            modifier = Modifier
        )
    }

    @Composable
    private fun LoginRequiredContent(
        onDefaultLoginRequested: () -> Unit,
        onSignupClicked: () -> Unit,
        onSocialProviderLoginRequested: (SocialLoginProvider) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val strings = LocalAuthenticationStrings.current.loginRequiredStrings

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp),
            modifier = modifier.padding(20.dp),
        ) {
            Image(
                painter = painterResource(SharedDesignCoreResources.images.bp_logo_dark),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = strings.title,
                    size = TextSize.XLarge,
                    weight = FontWeight.Bold
                )

                Text(
                    text = strings.subtitle,
                    size = TextSize.Large,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier,
            ) {
                LoginOptionButton(
                    label = strings.defaultOptionLabel,
                    icon = SharedDesignCoreResources.images.ic_profile_circle,
                    onClick = onDefaultLoginRequested,
                    modifier = Modifier.fillMaxWidth(),
                )

                SocialLoginProvider.entries.forEach { socialProvider ->
                    LoginOptionButton(
                        label = when (socialProvider) {
                            SocialLoginProvider.Gmail -> strings.googleOptionLabel
                            SocialLoginProvider.Facebook -> strings.facebookOptionLabel
                        },
                        icon = socialProvider.resource,
                        onClick = { onSocialProviderLoginRequested(socialProvider) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            AccountCreationOption(
                authenticationFlow = AuthenticationFlow.Signup,
                onSignupClicked = onSignupClicked,
                modifier = Modifier,
            )
        }
    }

    @Composable
    private fun LoginOptionButton(
        label: String,
        icon: ImageResource,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .clip(BeautifulShape.Rounded.Regular.composeShape)
                .clickable { onClick() }
                .background(BeautifulColor.Surface.composeColor)
                .padding(
                    vertical = 4.dp,
                    horizontal = 10.dp,
                ),
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
            )

            Text(
                text = label,
                size = TextSize.Large,
                weight = FontWeight.Bold,
            )
        }
    }
}
