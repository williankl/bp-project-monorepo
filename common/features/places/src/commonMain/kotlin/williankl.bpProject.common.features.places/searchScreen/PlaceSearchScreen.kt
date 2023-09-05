package williankl.bpProject.common.features.places.searchScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.delay
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.features.places.Divider
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.Companion.MINIMUM_SEARCH_LENGTH
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.Companion.queryDebounce
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.PlaceSearchPresentation
import williankl.bpProject.common.platform.design.components.maps.MapsComponent
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonType
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.input.Input
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.stateHandler.bpScreen.BeautifulScreen

public data class PlaceSearchScreen(
    private val onPlaceCreated: (MapPlaceResult) -> Unit,
) : BeautifulScreen() {

    @Composable
    override fun BeautifulContent() {
        val runnerModel = rememberScreenModel<PlaceSearchRunnerModel>()
        val presentation by runnerModel.currentData.collectAsState()

        var searchQuery by remember {
            mutableStateOf("")
        }

        LaunchedEffect(presentation.selectedAddress) {
            searchQuery = presentation.selectedAddress?.displayName.orEmpty()
        }

        LaunchedEffect(searchQuery) {
            delay(queryDebounce)
            if (searchQuery.length > MINIMUM_SEARCH_LENGTH) {
                runnerModel.queryFor(searchQuery)
            }
        }

        PlaceSearchContent(
            presentation = presentation,
            searchQuery = searchQuery,
            selectedLocation = presentation.selectedAddress,
            onSearchQueryChanged = { searchQuery = it },
            onMapAddressSelected = { runnerModel.updateAddressValue(it) },
            onCoordinateSelected = {
                runnerModel.updateFromCoordinate(
                    coordinate = it,
                )
            },
            onContinueClicked = {
                presentation.selectedAddress?.let(onPlaceCreated)
            },
            modifier = Modifier
                .background(BeautifulColor.Background.composeColor)
                .fillMaxSize()
        )
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun PlaceSearchContent(
        presentation: PlaceSearchPresentation,
        searchQuery: String,
        selectedLocation: MapPlaceResult?,
        onSearchQueryChanged: (String) -> Unit,
        onMapAddressSelected: (MapPlaceResult?) -> Unit,
        onCoordinateSelected: (MapCoordinate) -> Unit,
        onContinueClicked: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val strings = LocalPlacesStrings.current.placeSearchStrings
        val focusManager = LocalFocusManager.current

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
                    onSearch = { focusManager.clearFocus(true) }
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState -> isFocusOnInput = focusState.isFocused }
            )

            AnimatedContent(
                targetState = isFocusOnInput,
                transitionSpec = { fadeIn() with fadeOut() },
                modifier = Modifier.weight(1f)
            ) { shouldShowResultList ->
                if (shouldShowResultList) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        itemsIndexed(presentation.queryResults) { index, result ->
                            PlaceOption(
                                label = result.displayName,
                                description = with(result.address) {
                                    "$street\n$city ($state) - $country"
                                },
                                onClicked = {
                                    focusManager.clearFocus(force = true)
                                    onMapAddressSelected(result)
                                },
                                modifier = Modifier.fillMaxWidth(),
                            )

                            if (index != presentation.queryResults.lastIndex) {
                                Divider()
                            }
                        }
                    }
                } else {
                    MapsComponent(
                        currentMarkedPlace = selectedLocation?.coordinate,
                        onClearPlaceRequested = { onMapAddressSelected(null) },
                        onPlaceSelected = onCoordinateSelected,
                        modifier = Modifier.fillMaxSize(),
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

    @Composable
    private fun PlaceOption(
        label: String,
        description: String,
        onClicked: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier
                .clickable { onClicked() }
                .padding(12.dp)
        ) {
            Text(
                text = label,
                color = BeautifulColor.NeutralHigh,
                weight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = description,
                color = BeautifulColor.NeutralHigh,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
