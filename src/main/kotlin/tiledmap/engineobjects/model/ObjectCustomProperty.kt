package tiledmap.engineobjects.model

import org.joml.Vector4f

data class ObjectCustomProperty(
    val name: String,
    val type: CustomPropertyType,
    val propertyType: String? = null,
    val intValue: Int? = null,
    val stringValue: String? = null,
    val floatValue: Float? = null,
    val boolValue: Boolean? = null,
    val colorValue: Vector4f? = null,
    val fileValue: String? = null,
    val classValue: Map<String, ObjectCustomProperty>? = null
)