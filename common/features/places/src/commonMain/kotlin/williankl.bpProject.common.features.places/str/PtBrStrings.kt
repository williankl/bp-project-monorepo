package williankl.bpProject.common.features.places.str

import cafe.adriel.lyricist.LyricistStrings
import williankl.bpProject.common.features.places.str.PlacesStrings.*
import williankl.bpProject.common.features.places.str.PlacesStrings.PhotoSelectionStrings
import williankl.bpProject.common.features.places.str.PlacesStrings.PlaceCreationStrings
import williankl.bpProject.common.features.places.str.PlacesStrings.PlaceDetailsStrings

@LyricistStrings(languageTag = "pt-BR", default = true)
internal val ptBrStrings = PlacesStrings(
    distanceLabel = { distanceInMeters ->
        if (distanceInMeters > 1000) {
            "${(distanceInMeters / 1000)}km"
        } else {
            "${distanceInMeters}m"
        }
    },
    photoSelectionStrings = PhotoSelectionStrings(
        nextActionLabel = "Próximo",
    ),
    placeCreationStrings = PlaceCreationStrings(
        locationLabel = "Localização",
        searchLocationLabel = "Procurar",
        seasonLabel = "Estação do ano",
        curiosityInputHintLabel = "Escreva uma curiosidade...",
        costInputHintLabel = "Valor médio para acesso, caso precise.",
        publishLabel = "Publicar",
    ),
    placeDetailsStrings = PlaceDetailsStrings(
        title = "Postagem",
        ratingsLabel = { quantity ->
            if (quantity > 1) "($quantity Avaliações)"
            else null
        },
        addToRouteLabel = "Adicionar no meu roteiro",
        favouriteLabel = "Favoritar",
        unFavouriteLabel = "Remover dos favoritos",
        inAppRatingLabel = "Avaliações",
        addRatingLabel = "Fazer comentário",
        addRatingActionLabel = "Enviar",
        commentHint = "Comentar",
    ),
    placeSearchStrings = PlaceSearchStrings(
        nextLabel = "Próximo",
        localizationLabel = "Localização",
    )
)
