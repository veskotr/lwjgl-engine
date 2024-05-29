import engine.deltaTime
import io.isKeyDown
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_D
import physics.Collider
import physics.ICollisionListener
import structure.EngineComponent

class SampleComponent : EngineComponent(), ICollisionListener {


    override fun start() {
    }

    override fun update() {
        if (isKeyDown(GLFW_KEY_A)) {
            setLinearVelocity(Vector2f(-10000 * deltaTime, 0f))
        } else if (isKeyDown(GLFW_KEY_D)) {
            setLinearVelocity(Vector2f(10000 * deltaTime, 0f))
        }
    }

    override fun onCollisionEnter(other: Collider) {
        println("Collision detected!")
    }

    override fun onCollisionExit(other: Collider) {
        println("Collision ended!")
    }
}