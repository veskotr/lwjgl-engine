package io

import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import structure.EngineObject

class Camera private constructor(val width: Int, val height: Int) {

    companion object {
        private var instance: Camera? = null

        fun getInstance(width: Int, height: Int): Camera {
            if (instance == null) {
                instance = Camera(width, height)
            }
            return instance!!
        }
    }

    var projection: Matrix4f = Matrix4f()
        get() {
            return field.scale(Vector3f(scale, 0f)).translate(Vector3f(position, 0f), Matrix4f())
        }

    var position: Vector2f = Vector2f(0f)

    var scale: Vector2f = Vector2f(1f)

    val size: Vector2f = Vector2f(width.toFloat(), height.toFloat())

    init {
        projection = Matrix4f().ortho2D(-width / 2.0f, width / 2.0f, -height / 2.0f, height / 2.0f)
    }

    fun isInView(engineObject: EngineObject): Boolean {

        val cameraPosition = position
        return engineObject.getPosition().x < -cameraPosition.x + size.x  &&
                engineObject.getPosition().x + engineObject.getScale().x > -cameraPosition.x - size.x  &&
                engineObject.getPosition().y < cameraPosition.y + size.y  &&
                engineObject.getPosition().y + engineObject.getScale().y > cameraPosition.y - size.y
    }
}