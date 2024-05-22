package structure

import org.joml.Vector2f

abstract class EngineComponent : IEngineObject {
    var active = false

    lateinit var parentObject: EngineObject

    fun getPosition(): Vector2f {
        return parentObject.transform.position
    }

    fun getRotation(): Float {
        return parentObject.transform.rotation
    }

    fun getScale(): Vector2f {
        return parentObject.transform.scale
    }

    fun setPosition(position: Vector2f) {
        parentObject.transform.position = position
    }

    fun setRotation(rotation: Float) {
        parentObject.transform.rotation = rotation
    }

    fun setScale(scale: Vector2f) {
        parentObject.transform.scale = scale
    }
}