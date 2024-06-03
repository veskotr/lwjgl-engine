import engine.camera
import engine.deltaTime
import io.isKeyDown
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW.*
import physics.Collider
import physics.ICollisionListener
import structure.EngineComponent

class SampleComponent : EngineComponent(), ICollisionListener {

    override fun start() {
        camera.scale = Vector2f(1f)
    }

    override fun update() {
        if (isKeyDown(GLFW_KEY_A)) {
            camera.position.x += 1000 * deltaTime
        }
        if (isKeyDown(GLFW_KEY_D)) {
            camera.position.x -= 1000 * deltaTime
        }
        if (isKeyDown(GLFW_KEY_W)) {
            camera.position.y -= 1000 * deltaTime
        }
        if (isKeyDown(GLFW_KEY_S)) {
            camera.position.y += 1000 * deltaTime
        }
    }

    override fun onCollisionEnter(other: Collider) {
        println("Collision detected!")
    }

    override fun onCollisionExit(other: Collider) {
        println("Collision ended!")
    }
}