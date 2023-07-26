package williankl.bpProject.common.data.placeService

import com.benasher44.uuid.Uuid
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import williankl.bpProject.common.core.models.PlaceData
import williankl.bpProject.common.core.models.PlaceData.PlaceDataAddress.PlaceAddressCoordinate
import williankl.bpProject.common.core.models.UserData
import williankl.bpProject.common.core.services.PlaceService
import williankl.bpProject.common.data.placeService.models.PlacesAddressFirebaseModel
import williankl.bpProject.common.data.placeService.models.PlacesFirebaseModel
import williankl.bpProject.common.data.placeService.models.firebaseTag
import williankl.bpProject.common.data.placeService.models.placeDataSeason

internal class PlaceServiceInfrastructure : PlaceService {

    private val placesReference = Firebase.firestore.collection("places")
    private val placesAddressReference = Firebase.firestore.collection("addresses")
    override suspend fun createPlace(
        userData: UserData,
        placeData: PlaceData,
    ) {
        val createdAddress = with(placeData.address) {
            PlacesAddressFirebaseModel(
                id = Uuid.randomUUID().toString(),
                placeId = placeData.id,
                city = city,
                street = street,
                country = country,
                latitude = coordinates.latitude,
                longitude = coordinates.longitude,
            )
        }

        placesReference
            .add(
                PlacesFirebaseModel(
                    id = placeData.id,
                    ownerId = userData.id,
                    addressId = createdAddress.id,
                    name = placeData.name,
                    description = placeData.description,
                    season = placeData.season.firebaseTag,
                    imageUrls = placeData.imageUrls,
                )
            )
            .retrieve()

        placesAddressReference
            .add(createdAddress)
            .retrieve()
    }

    override suspend fun retrievePlace(id: String): PlaceData? {
        val placeResult = placesReference
            .whereEqualTo("id", id)
            .lastOrNull<PlacesFirebaseModel>()

        val placeAddressResult = placesAddressReference
            .whereEqualTo("placeId", placeResult?.addressId)
            .lastOrNull<PlacesAddressFirebaseModel>()

        return if (placeResult != null && placeAddressResult != null) {
            PlaceData(
                id = placeResult.id,
                ownerId = placeResult.ownerId,
                name = placeResult.name,
                description = placeResult.description,
                imageUrls = placeResult.imageUrls,
                season = placeResult.season.placeDataSeason,
                address = PlaceData.PlaceDataAddress(
                    street = placeAddressResult.street,
                    city = placeAddressResult.city,
                    country = placeAddressResult.country,
                    coordinates = PlaceAddressCoordinate(
                        latitude = placeAddressResult.latitude,
                        longitude = placeAddressResult.longitude,
                    ),
                ),
            )
        } else null
    }
}