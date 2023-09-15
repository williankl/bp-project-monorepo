package williankl.bpProject.common.features.authentication.modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.features.authentication.LocalAuthenticationStrings
import williankl.bpProject.common.features.authentication.components.AccountCreationOption
import williankl.bpProject.common.features.authentication.models.AuthenticationFlow
import williankl.bpProject.common.features.authentication.models.SocialLoginProvider
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

public class LoginRequiredBottomSheet : BeautifulScreen() {

    @Composable
    override fun BeautifulContent() {
        LoginRequiredContent(
            onDefaultLoginRequested = {},
            onSignupClicked = {},
            onSocialProviderLoginRequested = {},
            modifier = Modifier.fillMaxSize()
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
            modifier = modifier,
        ) {
            Image(
                painter = painterResource(SharedDesignCoreResources.images.bp_logo_dark),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Text(
                text = strings.title,
                size = TextSize.XLarge,
                weight = FontWeight.Bold
            )

            Text(
                text = strings.subtitle,
                size = TextSize.Large,
            )

            LoginOptionButton(
                label = strings.defaultOptionLabel,
                icon = SharedDesignCoreResources.images.ic_profile_circle,
                onClick = onDefaultLoginRequested,
                modifier = Modifier,
            )

            SocialLoginProvider.entries.forEach { socialProvider ->
                LoginOptionButton(
                    label = when (socialProvider) {
                        SocialLoginProvider.Gmail -> strings.googleOptionLabel
                        SocialLoginProvider.Facebook -> strings.facebookOptionLabel
                    },
                    icon = socialProvider.resource,
                    onClick = { onSocialProviderLoginRequested(socialProvider) },
                    modifier = Modifier,
                )
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            AccountCreationOption(
                authenticationFlow = AuthenticationFlow.Signup,
                onSignupClicked = onSignupClicked,
                modifier = Modifier.padding(top = 16.dp),
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