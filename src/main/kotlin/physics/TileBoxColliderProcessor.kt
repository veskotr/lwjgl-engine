package physics

import mu.KotlinLogging
import org.joml.Vector2f
import structure.EngineObject
import tiledmap.engineobjects.TileObjectProcessor
import tiledmap.engineobjects.model.ObjectProperties

class TileBoxColliderProcessor : TileObjectProcessor<BoxCollider> {

    private val logger = KotlinLogging.logger {}

    override fun processObjectToComponent(tile: EngineObject, objectProperties: ObjectProperties): BoxCollider {
        logger.info { "Extracted position of ${objectProperties.name}: X: ${objectProperties.position.x}, Y: ${objectProperties.position.y}" }
        val center = tile.getScale()
        val pos = Vector2f(objectProperties.position.x - center.x, center.y - objectProperties.position.y)
        pos.add(objectProperties.size.mul(Vector2f(1f, -1f), Vector2f()))
        logger.info { "Calculated position: X: ${pos.x}, Y: ${pos.y}" }
        return BoxCollider(size = objectProperties.size, offsetPosition = pos, isSensor = false)
    }
}