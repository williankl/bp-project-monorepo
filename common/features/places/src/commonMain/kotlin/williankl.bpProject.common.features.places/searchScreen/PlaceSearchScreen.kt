package williankl.bpProject.common.features.places.searchScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.platform.design.components.maps.MapCoordinate
import williankl.bpProject.common.platform.design.components.maps.MapsComponent
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonType
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.input.Input
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

public object PlaceSearchScreen : BeautifulScreen() {

    @Composable
    override fun BeautifulContent() {
        var selectedLocation by remember {
            mutableStateOf<MapCoordinate?>(null)
        }

        var searchQuery by remember {
            mutableStateOf("")
        }

        PlaceSearchContent(
            searchQuery = searchQuery,
            selectedLocation = selectedLocation,
            onSearchQueryChanged = { searchQuery = it },
            onLocationClicked = { selectedLocation = it },
            onContinueClicked = { /* todo - handle flow */ },
            modifier = Modifier
                .background(BeautifulColor.Background.composeColor)
                .fillMaxSize()
        )
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun PlaceSearchContent(
        searchQuery: String,
        selectedLocation: MapCoordinate?,
        onSearchQueryChanged: (String) -> Unit,
        onLocationClicked: (MapCoordinate?) -> Unit,
        onContinueClicked: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val strings = LocalPlacesStrings.current.placeSearchStrings

        val focusRequester = remember {
            FocusRequester()
        }

        var isFocusOnInput by remember {
            mutableStateOf(true)
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .padding(vertical = 16.dp)
        ) {
            Input(
                text = searchQuery,
                onTextChange = onSearchQueryChanged,
                startContent = {
                    Image(
                        painter = painterResource(SharedDesignCoreResources.images.ic_camera),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                        modifier = Modifier.size(30.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { focusRequester.freeFocus() }
                ),
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState -> isFocusOnInput = focusState.isFocused }
            )

            AnimatedContent(
                targetState = isFocusOnInput,
                modifier = Modifier.weight(1f)
            ) { shouldShowResultList ->
                if (shouldShowResultList) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {

                    }
                } else {
                    MapsComponent(
                        currentMarkedPlace = selectedLocation,
                        onClearPlaceRequested = { onLocationClicked(null) },
                        onPlaceSelected = onLocationClicked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    )
                }
            }

            Button(
                label = strings.nextLabel,
                onClick = onContinueClicked,
                modifier = Modifier.align(Alignment.End),
                variant = ButtonVariant.Secondary,
                enabled = selectedLocation != null,
                type = ButtonType.Pill,
            )
        }
    }

}