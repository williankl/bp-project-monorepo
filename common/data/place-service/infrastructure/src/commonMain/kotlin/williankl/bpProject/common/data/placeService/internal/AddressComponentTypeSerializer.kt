package williankl.bpProject.common.data.placeService.internal

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import williankl.bpProject.common.data.placeService.models.AddressComponentType

internal class AddressComponentTypeSerializer : KSerializer<AddressComponentType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("AddressComponentType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): AddressComponentType {
        return fromCode(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: AddressComponentType) {
        encoder.encodeString(value.key)
    }

    private fun fromCode(code: String): AddressComponentType {
        return AddressComponentType.entries
            .firstOrNull { componentType -> componentType.key == code }
            ?: AddressComponentType.Unknown
    }
}
