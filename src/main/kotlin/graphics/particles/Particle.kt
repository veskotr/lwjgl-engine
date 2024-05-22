package graphics.particles

import engine.deltaTime
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f

class Particle(
    private val size: Float,
    private val position: Vector2f,
    private val velocity: Vector2f,
    private val life: Int,
    private val useWorldSpace: Boolean
) {

    val matrix: Matrix4f
    var aliveTime = 0

    var alive = true

    init {
        matrix = Matrix4f().identity()
        matrix.scale(size, size, 1f)
    }

    fun update(position: Vector2f) {
        if (aliveTime >= life) {
            alive = false
            return
        }

        val finalPosition = if (useWorldSpace) {
            this.position.add(velocity.mul(deltaTime, Vector2f()))
        } else {
            position.add(velocity.mul(deltaTime, Vector2f()))
        }

        matrix.setTranslation(Vector3f(finalPosition, 0f))
        aliveTime++
    }


}