package williankl.bpProject.common.features.places.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.delay
import williankl.bpProject.common.core.models.MapCoordinate
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.data.placeService.models.MapPlaceResult
import williankl.bpProject.common.features.places.Divider
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.features.places.create.handler.CreationHandler
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.Companion.MINIMUM_SEARCH_LENGTH
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.Companion.queryDebounce
import williankl.bpProject.common.features.places.searchScreen.PlaceSearchRunnerModel.PlaceSearchPresentation
import williankl.bpProject.common.platform.design.components.ImagePager
import williankl.bpProject.common.platform.design.components.maps.MapsComponent
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.button.ButtonType
import williankl.bpProject.common.platform.design.core.button.ButtonVariant
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.input.Input
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

internal class PlaceDetailsScreen(
    private val place: Place,
) : BeautifulScreen() {


    @Composable
    override fun BeautifulContent() {

    }

    @Composable
    private fun PlaceDetailsContent(
        place: Place,
    ){

    }

    @Composable
    private fun PlaceDetailsHeader(
        modifier: Modifier = Modifier,
    ){
        Column(
            modifier = modifier,
        ) {
            ImagePager(
                images = ,

            )
        }
    }


}
