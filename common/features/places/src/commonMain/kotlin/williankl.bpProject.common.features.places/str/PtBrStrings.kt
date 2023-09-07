package williankl.bpProject.common.features.places.str

import cafe.adriel.lyricist.LyricistStrings
import williankl.bpProject.common.features.places.str.PlacesStrings.*
import williankl.bpProject.common.features.places.str.PlacesStrings.PhotoSelectionStrings
import williankl.bpProject.common.features.places.str.PlacesStrings.PlaceCreationStrings
import williankl.bpProject.common.features.places.str.PlacesStrings.PlaceSearchStrings

@LyricistStrings(languageTag = "pt-BR", default = true)
internal val ptBrStrings = PlacesStrings(
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
    placeSearchStrings = PlaceSearchStrings(
        nextLabel = "Próximo"
    )
)
