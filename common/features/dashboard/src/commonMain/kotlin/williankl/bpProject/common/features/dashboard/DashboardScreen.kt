package williankl.bpProject.common.features.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.data.imageRetrievalService.controller.LocalImageRetrievalController
import williankl.bpProject.common.features.dashboard.models.DashboardActions
import williankl.bpProject.common.features.dashboard.pages.home.HomePage
import williankl.bpProject.common.features.dashboard.pages.userProfile.UserProfilePage
import williankl.bpProject.common.platform.design.components.toolbar.ToolbarHandler
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.clickableIcon
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen
import williankl.bpProject.common.platform.stateHandler.navigation.LocalRouter
import williankl.bpProject.common.platform.stateHandler.navigation.models.Authentication
import williankl.bpProject.common.platform.stateHandler.navigation.models.Places.PlacePhotoSelection

public data class DashboardScreen(
    private val initialTab: DashboardTab = DashboardTab.Home
) : BeautifulScreen() {

    public enum class DashboardTab {
        Home, Profile
    }

    private val options by lazy {
        DashboardActions.entries.toList()
    }

    @Composable
    override fun initialToolbarConfig(
        navigator: Navigator,
        toolbarHandler: ToolbarHandler,
    ) {
        super.initialToolbarConfig(navigator, toolbarHandler)
        val runnerModel = rememberScreenModel<DashboardTab, DashboardRunnerModel>(arg = initialTab)

        when (runnerModel.currentTab) {
            DashboardActions.Profile -> {
                toolbarHandler.label = null
                toolbarHandler.backgroundColor = BeautifulColor.Background
                toolbarHandler.trailingIcons = listOf(
                    ToolbarHandler.ToolbarAction(
                        icon = SharedDesignCoreResources.images.ic_profile,
                        onClick = { /* todo -> fix these */ }
                    ),
                    ToolbarHandler.ToolbarAction(
                        icon = SharedDesignCoreResources.images.ic_profile,
                        onClick = { /* todo -> fix these */ }
                    ),
                )
            }

            DashboardActions.Home -> {
                val strings = LocalDashboardStrings.current
                toolbarHandler.label = strings.projectName
                toolbarHandler.backgroundColor = BeautifulColor.BackgroundHigh
            }

            else -> {
                toolbarHandler.backgroundColor = BeautifulColor.Background
                toolbarHandler.label = null
            }
        }

    }

    @Composable
    override fun BeautifulContent() {
        val runnerModel = rememberScreenModel<DashboardTab, DashboardRunnerModel>(arg = initialTab)
        val imageRetrievalController = LocalImageRetrievalController.currentOrThrow
        val router = LocalRouter.currentOrThrow

        DashboardScreenContent(
            currentAction = runnerModel.currentTab,
            onOptionSelected = { selectedAction ->
                when (selectedAction) {
                    DashboardActions.Home -> runnerModel.currentTab = DashboardActions.Home
                    DashboardActions.Profile ->
                        if (true) {
                            runnerModel.currentTab = DashboardActions.Profile
                        } else {
                            router.showBottomSheet(
                                Authentication.LoginRequiredBottomSheet
                            )
                        }

                    DashboardActions.Add ->
                        imageRetrievalController.showBottomSheet(router.bottomSheetNavigator) { result ->
                            router.push(PlacePhotoSelection(result))
                        }
                }
            },
            modifier = Modifier
                .background(BeautifulColor.Surface.composeColor)
                .fillMaxSize(),
        )
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun DashboardScreenContent(
        currentAction: DashboardActions?,
        onOptionSelected: (DashboardActions) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                AnimatedContent(
                    modifier = Modifier.fillMaxSize(),
                    transitionSpec = { fadeIn() with fadeOut() },
                    targetState = when (currentAction) {
                        DashboardActions.Profile -> UserProfilePage
                        else -> HomePage
                    },
                    content = { currentPage ->
                        currentPage.BeautifulContent()
                    }
                )
            }

            OptionsBar(
                currentAction = currentAction,
                onOptionSelected = onOptionSelected,
                modifier = Modifier
                    .background(BeautifulColor.BackgroundHigh.composeColor)
                    .padding(12.dp)
                    .fillMaxWidth()
            )
        }
    }

    @Composable
    private fun OptionsBar(
        currentAction: DashboardActions?,
        onOptionSelected: (DashboardActions) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            options.forEach { option ->
                val (tintColor, backgroundColor) = if (option == currentAction) {
                    BeautifulColor.PrimaryHigh to BeautifulColor.SecondaryHigh
                } else {
                    BeautifulColor.SecondaryHigh to BeautifulColor.Transparent
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickableIcon { onOptionSelected(option) },
                ) {
                    Image(
                        painter = painterResource(option.icon),
                        colorFilter = ColorFilter.tint(tintColor.composeColor),
                        contentDescription = null,
                        modifier = Modifier
                            .background(
                                shape = CircleShape,
                                color = backgroundColor.composeColor,
                            )
                            .padding(6.dp)
                            .size(24.dp)
                    )
                }
            }
        }
    }
}
