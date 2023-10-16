package williankl.bpProject.server.database.internal.image

import app.cash.sqldelight.driver.jdbc.JdbcDriver
import com.benasher44.uuid.Uuid
import williankl.bpProject.common.core.models.Place.ImageData
import williankl.bpProject.server.database.internal.DriverProvider.withDatabase
import williankl.bpProject.server.database.internal.image.Mapper.fromDomain
import williankl.bpProject.server.database.internal.image.Mapper.toDomain
import williankl.bpProject.server.database.services.ImageStorage

internal class ImageStorageInfrastructure(
    private val driver: JdbcDriver,
) : ImageStorage {
    override fun storeImage(
        placeId: Uuid,
        data: ImageData
    ) {
        withDatabase(driver) {
            imageDataQueries.create(
                fromDomain(
                    placeId = placeId,
                    data = data,
                )
            )
        }
    }

    override fun imagesFromPlace(placeId: Uuid): List<ImageData> {
        return withDatabase(driver) {
            imageDataQueries.imagesForPlace(placeId)
                .executeAsList()
                .map(::toDomain)
        }
    }
}
