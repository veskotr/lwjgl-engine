package structure

import org.joml.Vector2f
import physics.Collider

abstract class EngineComponent : IEngineObject {
    var active = false

    lateinit var parentObject: EngineObject

    fun getPosition(): Vector2f {
        return parentObject.getPosition()
    }

    fun getRotation(): Float {
        return parentObject.getRotation()
    }

    fun getScale(): Vector2f {
        return parentObject.getScale()
    }

    fun setPosition(position: Vector2f) {
        parentObject.setPosition(position)
    }

    fun setRotation(rotation: Float) {
        parentObject.setRotation(rotation)
    }

    fun setScale(scale: Vector2f) {
        parentObject.setScale(scale)
    }

    fun setLinearVelocity(velocity: Vector2f) {
        val collider: Collider? = parentObject.getComponent(Collider::class) as Collider?
        if (collider != null) {
            collider.setPhysicsLinearVelocity(velocity)
        } else {
            getPosition().add(velocity)
        }
    }
}