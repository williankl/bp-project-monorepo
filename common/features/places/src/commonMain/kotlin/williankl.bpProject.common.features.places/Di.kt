package williankl.bpProject.common.features.places

import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import williankl.bpProject.common.features.places.photoSelection.PhotoSelectionRunnerModel

public val placesDi: DI.Module = DI.Module("williankl.bpProject.common.features.places") {
    bindProvider {
        PhotoSelectionRunnerModel(
            imageRetriever = instance(),
            dispatcher = Dispatchers.Default,
        )
    }
}
