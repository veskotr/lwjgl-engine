package physics

import org.jbox2d.dynamics.BodyType
import org.joml.Vector2f
import structure.EngineComponent
import structure.EngineObject
import tiledmap.chunks.X
import tiledmap.chunks.Y
import tiledmap.engineobjects.EngineComponentProcessor
import tiledmap.engineobjects.model.ObjectCustomProperty
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.tilesets.TileSet

class BoxColliderComponentProcessor : EngineComponentProcessor {

    companion object {
        private const val OFFSET_POSITION = "offsetPosition"
        private const val SIZE = "size"
    }

    override fun processEngineComponent(
        engineObject: EngineObject,
        objectProperties: ObjectProperties,
        tileSets: List<TileSet>,
        path: String,
        customProperty: ObjectCustomProperty
    ): EngineComponent {
        val offsetPosition = getOffsetPosition(customProperty)
        val size = getSize(customProperty)
        return BoxCollider(size, offsetPosition, bodyType = BodyType.DYNAMIC, isSensor = false)
    }

    private fun getOffsetPosition(customProperty: ObjectCustomProperty): Vector2f {
        return Vector2f(
            customProperty.classValue?.get(OFFSET_POSITION)?.classValue?.get(X)?.floatValue ?: 0f,
            customProperty.classValue?.get(OFFSET_POSITION)?.classValue?.get(Y)?.floatValue ?: 0f,
        )
    }

    private fun getSize(customProperty: ObjectCustomProperty): Vector2f {
        return Vector2f(
            customProperty.classValue?.get(SIZE)?.classValue?.get(X)?.floatValue ?: 0f,
            customProperty.classValue?.get(SIZE)?.classValue?.get(Y)?.floatValue ?: 0f,
        )
    }
}