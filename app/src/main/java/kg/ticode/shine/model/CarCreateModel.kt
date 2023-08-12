package kg.ticode.shine.model

import kg.ticode.shine.dto.CarDTO
import java.io.File

data class CarCreateModel(
    val carDTO: CarDTO,
    val files: Array<File>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CarCreateModel

        if (carDTO != other.carDTO) return false
        if (!files.contentEquals(other.files)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = carDTO.hashCode()
        result = 31 * result + files.contentHashCode()
        return result
    }
}
