package physics

import tiledmap.engineobjects.TileObjectProcessor
import tiledmap.engineobjects.model.ObjectProperties

class TileBoxColliderProcessor : TileObjectProcessor<BoxCollider> {
    override fun processObjectToComponent(objectProperties: ObjectProperties): BoxCollider {
        return BoxCollider(size = objectProperties.size, offsetPosition = objectProperties.position)
    }
}