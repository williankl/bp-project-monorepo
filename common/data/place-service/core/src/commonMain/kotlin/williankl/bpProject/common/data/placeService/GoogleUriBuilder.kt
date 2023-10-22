package williankl.bpProject.common.data.placeService

import williankl.bpProject.common.core.models.MapCoordinate

public object GoogleUriBuilder {

    private const val MAPS_URI = "comgooglemaps://"

    public fun buildMultipleDestinationsUri(
        fromCoordinate: MapCoordinate,
        vararg toCoordinates: MapCoordinate,
    ): String {
        val coordinateDirections = toCoordinates
            .joinToString(separator = "+") { coordinate -> coordinate.parseString() }

        return "$MAPS_URI?saddr=${fromCoordinate.parseString()}&daddr=$coordinateDirections"
    }

    private fun MapCoordinate.parseString(): String = "$latitude,$longitude"
}
