package debug.collision

import debug.rendering.AbstractDebugRenderer
import engine.camera
import geometry.toVector2f
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import physics.Collider
import physics.INVERSE_SCALE_FACTOR

class CollisionDebugRenderer(private val collider: Collider) : AbstractDebugRenderer() {

    val model: CollisionMeshModel

    init {
        val vertices = collider.getCollisionMesh()
        model = CollisionMeshModel(vertices)
    }

    override fun render() {
        shader.bind()
        shader.setUniform("projection", camera.projection)
        shader.setUniform(
            "model", Matrix4f().translate(Vector3f(collider.body.position.toVector2f().mul(INVERSE_SCALE_FACTOR), 0f))
        )
        shader.setUniform(
            "color", Vector4f(1.0f, 0f, 0f, 1.0f)
        )
        model.bindModel()
        model.drawModel()
        model.unbindModel()

    }
}