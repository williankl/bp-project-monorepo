package williankl.bpProject.common.features.places.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.currentOrThrow
import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.PlaceRating
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.features.places.details.PlaceDetailsRunnerModel.PlaceDetailsPresentation
import williankl.bpProject.common.features.places.details.modal.RatingContent
import williankl.bpProject.common.platform.design.components.AsyncImagePager
import williankl.bpProject.common.platform.design.components.CommentBubble
import williankl.bpProject.common.platform.design.components.CommentBubbleAction
import williankl.bpProject.common.platform.design.components.StarRating
import williankl.bpProject.common.platform.design.components.TextContainer
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.design.core.text.TextSize
import williankl.bpProject.common.platform.stateHandler.LocalRouter
import williankl.bpProject.common.platform.stateHandler.navigation.models.Authentication
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen
import williankl.bpProject.common.platform.stateHandler.screen.toolbar.ToolbarConfig

public class PlaceDetailsScreen(
    private val place: Place,
) : BeautifulScreen() {

    override val toolbarConfig: ToolbarConfig
        @Composable get() = super.toolbarConfig.copy(
            label = LocalPlacesStrings.current.placeDetailsStrings.title
        )

    @Composable
    override fun BeautifulContent() {
        val router = LocalRouter.currentOrThrow
        val runnerModel = rememberScreenModel<Uuid, PlaceDetailsRunnerModel>(arg = place.id)
        val presentation by runnerModel.currentData.collectAsState()
        val ratingPaging by runnerModel.ratingPaging.collectAsState()

        PlaceDetailsContent(
            place = place,
            presentation = presentation,
            ratings = ratingPaging.items,
            onRatingOptionsRequested = { ratingId ->
            },
            onOptionSelected = { option ->
                when (option) {
                    is DetailsOptions.AddRoute -> Unit
                    is DetailsOptions.Address -> Unit
                    is DetailsOptions.Favourite -> Unit
                    is DetailsOptions.Owner -> Unit
                    is DetailsOptions.Season -> Unit
                    is DetailsOptions.Comment -> {
                        presentation.currentUser
                            ?.let { user ->
                                router.bottomSheetNavigator.show(
                                    RatingContent(
                                        user = user,
                                        onRating = { rating, comment ->
                                            router.hideBottomSheet()
                                            runnerModel.ratePlace(rating, comment)
                                        },
                                    )
                                )
                            }
                            ?: router.showBottomSheet(Authentication.LoginRequiredBottomSheet)
                    }
                }
            },
            modifier = Modifier
                .background(BeautifulColor.Background.composeColor)
                .fillMaxSize()
        )
    }

    @Composable
    private fun PlaceDetailsContent(
        presentation: PlaceDetailsPresentation,
        ratings: List<PlaceRating>,
        place: Place,
        onRatingOptionsRequested: (Uuid) -> Unit,
        onOptionSelected: (DetailsOptions) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        ) {
            item {
                PlaceDetailsHeader(
                    place = place,
                    presentation = presentation,
                    modifier = Modifier,
                )
            }

            item {
                OptionsContainer(
                    place = place,
                    onOptionSelected = onOptionSelected,
                    modifier = Modifier,
                )
            }

            if (ratings.isNotEmpty()) {
                commentItems(
                    ratings = ratings,
                    onRatingOptionsRequested = onRatingOptionsRequested,
                )
            }
        }
    }

    @Composable
    @OptIn(ExperimentalFoundationApi::class)
    private fun PlaceDetailsHeader(
        place: Place,
        presentation: PlaceDetailsPresentation,
        modifier: Modifier = Modifier,
    ) {
        val strings = LocalPlacesStrings.current
        val pagerState = rememberPagerState { place.images.size }
        var currentPageColor by remember {
            mutableStateOf(PlaceDetailsRunnerModel.defaultImageColor)
        }
        val animatedColor by animateColorAsState(currentPageColor)

        LaunchedEffect(pagerState.currentPage) {
            currentPageColor = presentation.averageColorList.getOrNull(pagerState.currentPage)
                ?: PlaceDetailsRunnerModel.defaultImageColor
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
            ) {
                Spacer(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    BeautifulColor.Transparent.composeColor,
                                    animatedColor,
                                    BeautifulColor.Transparent.composeColor,
                                )
                            )
                        )
                )

                AsyncImagePager(
                    urls = place.images,
                    state = pagerState,
                    pageSpacing = 34.dp,
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    modifier = Modifier
                        .heightIn(300.dp)
                        .padding(top = 16.dp),
                )
            }

            Text(
                text = place.displayName,
                weight = FontWeight.Bold,
                size = TextSize.Large,
            )

            Text(
                text = "${place.address.city}\n${place.address.country}",
                size = TextSize.Small,
                textAlign = TextAlign.Center,
            )

            AnimatedVisibility(
                visible = presentation.placeRatingData != null
            ) {
                if (presentation.placeRatingData != null) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        StarRating(
                            rating = presentation.placeRatingData.rating,
                            starSize = 14.dp,
                            modifier = Modifier
                        )

                        val label = strings.placeDetailsStrings.ratingsLabel(presentation.placeRatingData.ratingCount)
                        if (label != null) {
                            Text(
                                text = label,
                                size = TextSize.XSmall,
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .background(BeautifulColor.Border.composeColor)
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }

    @Composable
    private fun OptionsContainer(
        place: Place,
        onOptionSelected: (DetailsOptions) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val options = remember(place) {
            listOfNotNull(
                DetailsOptions.Owner(place.owner),
                if (place.seasons.isNotEmpty()) DetailsOptions.Season(place.seasons)
                else null,
                DetailsOptions.Address(place.address),
                DetailsOptions.Favourite,
                DetailsOptions.AddRoute,
                DetailsOptions.Comment,
            )
        }

        Column(
            modifier = modifier,
        ) {
            AnimatedVisibility(
                visible = place.description != null,
                modifier = Modifier,
            ) {
                place.description?.let { description ->
                    TextContainer(
                        text = description,
                        preOverflowMaxLines = 2,
                    )
                }
            }
            options.forEachIndexed { index, option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .clickable { onOptionSelected(option) }
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    option.header()

                    Text(
                        text = option.label(),
                        size = TextSize.Large,
                    )
                }

                if (index != options.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .background(BeautifulColor.Border.composeColor)
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .background(BeautifulColor.Border.composeColor)
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }

    private fun LazyListScope.commentItems(
        ratings: List<PlaceRating>,
        onRatingOptionsRequested: (Uuid) -> Unit,
    ) {
        item {
            Text(
                text = LocalPlacesStrings.current.placeDetailsStrings.inAppRatingLabel,
                weight = FontWeight.Bold,
                size = TextSize.XLarge,
                modifier = Modifier.padding(vertical = 16.dp),
            )
        }

        items(ratings) { rating ->
            CommentBubble(
                rating = rating,
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 6.dp,
                ),
                endAction = CommentBubbleAction(
                    icon = SharedDesignCoreResources.images.ic_horizontal_ellipsis,
                    onClick = { onRatingOptionsRequested(rating.id) },
                ),
            )
        }
    }
}
