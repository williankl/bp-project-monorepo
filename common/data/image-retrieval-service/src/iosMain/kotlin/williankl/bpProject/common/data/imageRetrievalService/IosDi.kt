package williankl.bpProject.common.data.imageRetrievalService

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import williankl.bpProject.common.data.imageRetrievalService.retriever.ImageRetriever

public actual val imageRetrievalServiceDi: DI.Module =
    DI.Module("williankl.bpProject.common.data.imageRetrievalService") {
        bindSingleton {
            ImageRetriever()
        }
    }