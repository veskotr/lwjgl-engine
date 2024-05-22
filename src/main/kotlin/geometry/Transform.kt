package geometry

import structure.EngineObject
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f

class Transform(private val engineObject: EngineObject) {

    var position: Vector2f = Vector2f()
    var rotation: Float = 0f
    var scale: Vector2f = Vector2f(1f, 1f)

    fun getTransformationMatrix(): Matrix4f {
        val transform = if (engineObject.parent != null) {
            Matrix4f(engineObject.parent!!.transform.getTransformationMatrixWithoutScale())
        } else {
            Matrix4f().identity()
        }
        transform.translate(Vector3f(position.x, position.y, 0f))
        transform.rotateZ(rotation)
        transform.scale(Vector3f(scale.x, scale.y, 1f))
        return transform
    }

    private fun getTransformationMatrixWithoutScale(): Matrix4f {
        val transform = if (engineObject.parent != null) {
            Matrix4f(engineObject.parent!!.transform.getTransformationMatrixWithoutScale())
        } else {
            Matrix4f().identity()
        }
        transform.translate(Vector3f(position.x, position.y, 0f))
        transform.rotateZ(rotation)
        return transform
    }

}
