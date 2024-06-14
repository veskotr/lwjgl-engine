package tiledmap.engineobjects.model

import org.joml.Vector2f

data class ObjectProperties(
    val id: Int,
    val name: String,
    val type: String,
    val position: Vector2f,
    val size: Vector2f,
    val rotation: Float,
    val tileId: Int?,
    val customProperties: Map<String, ObjectCustomProperty>
)