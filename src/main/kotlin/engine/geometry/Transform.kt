package engine.geometry

import engine.structure.EngineObject
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector2f
import org.joml.Vector3f

class Transform(private val engineObject: EngineObject) {

    var position: Vector2f = Vector2f()
    var rotation: Quaternionf = Quaternionf()
    var scale: Vector2f = Vector2f(1f, 1f)

    fun getLocalTransform(): Matrix4f {
        val transform = Matrix4f().translate(Vector3f(position.x, position.y, 0f))
        transform.rotate(rotation)
        transform.scale(Vector3f(scale.x, scale.y, 1f))
        return transform
    }

    fun getWorldTransform(): Matrix4f {
        var transform = getLocalTransform()
        var currentParent = engineObject.parent
        while (currentParent != null) {
            transform = currentParent.transform.getLocalTransform().mul(transform)
            currentParent = currentParent.parent
        }
        return transform
    }
}
