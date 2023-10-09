package williankl.bpProject.common.features.places.details.modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import williankl.bpProject.common.core.models.User
import williankl.bpProject.common.features.places.LocalPlacesStrings
import williankl.bpProject.common.platform.design.components.AsyncImage
import williankl.bpProject.common.platform.design.components.StarRating
import williankl.bpProject.common.platform.design.core.SharedDesignCoreResources
import williankl.bpProject.common.platform.design.core.button.Button
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.composeColor
import williankl.bpProject.common.platform.design.core.input.Input
import williankl.bpProject.common.platform.design.core.input.InputVariant
import williankl.bpProject.common.platform.design.core.text.Text
import williankl.bpProject.common.platform.stateHandler.screen.BeautifulScreen

internal class RatingContent(
    private val user: User,
    private val onRating: (Int, String?) -> Unit,
) : BeautifulScreen() {
    @Composable
    override fun BeautifulContent() {
        RatingScreenContent(
            user = user,
            onRating = onRating,
            modifier = Modifier.padding(16.dp),
        )
    }

    @Composable
    private fun RatingScreenContent(
        user: User,
        onRating: (Int, String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val strings = LocalPlacesStrings.current.placeDetailsStrings
        var writtenComment by remember {
            mutableStateOf("")
        }
        var selectedRating by remember {
            mutableStateOf<Int?>(null)
        }

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.Start),
            ) {
                AsyncImage(
                    url = user.avatarUrl.orEmpty(),
                    onError = {
                        Image(
                            painter = painterResource(SharedDesignCoreResources.images.ic_profile),
                            colorFilter = ColorFilter.tint(BeautifulColor.NeutralHigh.composeColor),
                            contentDescription = null,
                        )
                    },
                    modifier = Modifier.size(32.dp)
                )

                Text(
                    text = user.name,
                    weight = FontWeight.SemiBold,
                )
            }

            Input(
                text = writtenComment,
                onTextChange = { writtenComment = it },
                hint = strings.commentHint,
                variant = InputVariant.Primary,
                modifier = Modifier.fillMaxWidth(),
            )

            StarRating(
                rating = selectedRating?.toFloat() ?: 0f,
                onRating = { rating -> selectedRating = rating },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Button(
                label = strings.addRatingActionLabel,
                enabled = selectedRating != null,
                onClick = {
                    selectedRating?.let { rating ->
                        onRating(rating, writtenComment.ifBlank { null })
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
