package tiledmap.engineobjects.model

import org.joml.Vector4f

data class ObjectCustomProperty(val name: String,
                                val intValue: Int? = null,
                                val stringValue: String? = null,
                                val floatValue: Float? = null,
                                val boolValue: Boolean? = null,
                                val colorValue: Vector4f? = null,
                                val fileValue: String? = null) {
}