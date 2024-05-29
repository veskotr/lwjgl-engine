package graphics.particles

import engine.deltaTime
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f

class Particle(
    private val size: Float,
    private val position: Vector2f,
    private val velocity: Vector2f,
    val life: Int,
    private val useWorldSpace: Boolean
) {

    val matrix: Matrix4f = Matrix4f().identity()
    var aliveTime = 0

    var alive = true

    init {
        matrix.scale(size, size, 1f)
    }

    fun update() {
        if (aliveTime >= life) {
            alive = false
            return
        }

        val finalPosition = this.position.add(velocity.mul(deltaTime, Vector2f()))

        matrix.setTranslation(Vector3f(finalPosition, 0f))
        aliveTime++
    }


}