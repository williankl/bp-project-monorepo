package williankl.bpProject.common.data.placeService

import williankl.bpProject.common.core.models.MapCoordinate

public object GoogleUriBuilder {

    private const val MAPS_URI = "http://maps.google.com/maps"

    public fun buildRouteUri(
        fromCoordinate: MapCoordinate?,
        vararg toCoordinates: MapCoordinate,
    ): String {
        return if (fromCoordinate != null) {
            handleWithStartingLocation(
                fromCoordinate = fromCoordinate,
                toCoordinates = toCoordinates.toList()
            )
        } else {
            handleNoStartingLocation(toCoordinates.toList())
        }
    }

    private fun handleWithStartingLocation(
        fromCoordinate: MapCoordinate,
        toCoordinates: List<MapCoordinate>,
    ): String {
        return buildMapUri(fromCoordinate, toCoordinates)
    }

    private fun handleNoStartingLocation(
        toCoordinates: List<MapCoordinate>,
    ): String {
        val first = toCoordinates.firstOrNull() ?: error("No destination coordinate")

        val rest = if (toCoordinates.size > 1) {
            toCoordinates.toList()
                .subList(
                    fromIndex = 1,
                    toIndex = toCoordinates.lastIndex
                )
        } else emptyList()

        return buildMapUri(first, rest)
    }

    private fun MapCoordinate.parseString(): String = "$latitude,$longitude"

    private fun List<MapCoordinate>.parseJoinedString(): String =
        joinToString(separator = "+") { coordinate -> coordinate.parseString() }

    private fun buildMapUri(
        firstPlace: MapCoordinate,
        restOfCoordinates: List<MapCoordinate>
    ): String {
        val coordinateDirections = restOfCoordinates.parseJoinedString()
        return if (restOfCoordinates.isNotEmpty()) {
            "$MAPS_URI?saddr=${firstPlace.parseString()}&daddr=$coordinateDirections"
        } else {
            "$MAPS_URI?q=${firstPlace.parseString()}"
        }
    }
}
