package williankl.bpProject.common.features.places.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.core.models.Place
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.platform.design.components.AsyncImage
import williankl.bpProject.common.platform.design.components.ComposableString
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.shapes.BeautifulShape
import williankl.bpProject.common.core.models.Season as CoreSeason

internal sealed class DetailsOptions(
    val header: @Composable () -> Unit,
    val label: ComposableString,
) {
    internal class Owner(user: User) : DetailsOptions(
        header = {
            AsyncImage(
                url = user.avatarUrl.orEmpty(),
                onError = {
                    Image(
                        painter = painterResource(SharedDesignCoreResources.images.ic_profile),
                        colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                        contentDescription = null,
                    )
                },
                modifier = Modifier
                    .clip(BeautifulShape.Rounded.Circle.composeShape)
                    .size(24.dp),
            )
        },
        label = { user.tag ?: user.name },
    )

    internal data object Favourite : DetailsOptions(
        header = {
            Image(
                painter = painterResource(SharedDesignCoreResources.images.ic_heart),
                contentDescription = null,
                colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                modifier = Modifier.size(24.dp),
            )
        },
        label = { LocalPlacesStrings.current.placeDetailsStrings.favouriteLabel },
    )

    internal data object AddRoute : DetailsOptions(
        header = {
            Image(
                painter = painterResource(SharedDesignCoreResources.images.ic_map_route),
                contentDescription = null,
                colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                modifier = Modifier.size(24.dp),
            )
        },
        label = { LocalPlacesStrings.current.placeDetailsStrings.addToRouteLabel },
    )

    internal class Address(
        address: Place.PlaceAddress
    ) : DetailsOptions(
        header = {
            Image(
                painter = painterResource(SharedDesignCoreResources.images.ic_location_pin),
                contentDescription = null,
                colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                modifier = Modifier.size(24.dp),
            )
        },
        label = { "${address.street}\n${address.city} - ${address.country}" },
    )

    internal class Season(
        seasons: List<CoreSeason>
    ) : DetailsOptions(
        header = {
            Image(
                painter = painterResource(SharedDesignCoreResources.images.ic_flower),
                contentDescription = null,
                colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                modifier = Modifier.size(24.dp),
            )
        },
        label = {
            var resultStr = ""
            seasons.forEachIndexed { index, season ->
                resultStr += "${if (index == 0) "" else " | "}${season.label()}"
            }
            resultStr
        },
    )

    internal data object Comment : DetailsOptions(
        header = {
            Image(
                painter = painterResource(SharedDesignCoreResources.images.ic_chat_bubble),
                contentDescription = null,
                colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                modifier = Modifier.size(24.dp),
            )
        },
        label = { LocalPlacesStrings.current.placeDetailsStrings.addRatingLabel },
    )
}
