package engine.geometry

import engine.structure.EngineObject
import org.joml.*

/**
 * Created by vesko on 01.07.19.
 */
class Transform(private val engineObject: EngineObject) {
    private val localTransform: Matrix4f = Matrix4f()
    var frameVelocity: Vector2f = Vector2f()

    var position: Vector2f
        get() = if (engineObject.parent != null) {
            val transformedPosition = multiplyWithParent(engineObject.parent!!, localTransform)
            val dest = Vector4f()
            transformedPosition.transform(dest)
            Vector2f(dest.x, dest.y)
        } else {
            val dest = Vector4f()
            localTransform.transform(dest)
            Vector2f(dest.x, dest.y)
        }
        set(position) {
            localTransform.setTranslation(Vector3f(position, 0f))
        }

    private fun multiplyWithParent(parent: EngineObject, transform: Matrix4f): Matrix4f {
        val transformed = transform.mul(parent.getTransform()!!.localTransform, Matrix4f())
        return if (parent.parent != null) {
            multiplyWithParent(parent.parent!!, transformed)
        } else {
            transformed
        }
    }

    val localPosition: Vector2f
        get() {
            val dest = Vector4f()
            localTransform.transform(dest)
            return Vector2f(dest.x, dest.y)
        }
    var rotation: Quaternionf = Quaternionf()
        get() = localTransform.getUnnormalizedRotation(Quaternionf())

    fun rotate(rotation: Quaternionf?) {
        localTransform.rotate(rotation)
    }

    var scale: Vector2f = Vector2f(1f, 1f)
        get() {
            val dest = Vector3f()
            localTransform.getScale(dest)
            return Vector2f(dest.x, dest.y)
        }

    fun scale(scale: Vector2f) {
        localTransform.scale(Vector3f(scale, 0f))
    }

    fun getTransform(): Matrix4f {
        return if (engineObject.parent != null) {
            multiplyWithParent(engineObject.parent!!, localTransform)
        } else localTransform
    }
}