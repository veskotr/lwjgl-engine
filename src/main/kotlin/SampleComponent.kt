import engine.camera
import engine.deltaTime
import graphics.rendering.animations.AnimationsComponent
import io.isKeyDown
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_D
import org.lwjgl.glfw.GLFW.GLFW_KEY_S
import org.lwjgl.glfw.GLFW.GLFW_KEY_W
import physics.Collider
import physics.ICollisionListener
import engine.EngineComponent

class SampleComponent : EngineComponent(), ICollisionListener {

    lateinit var animationsComponent: AnimationsComponent

    override fun start() {
        camera.scale = Vector2f(1f)
        animationsComponent = parentObject.getComponent(AnimationsComponent::class)!! as AnimationsComponent
    }

    override fun update() {
        val velocity = Vector2f()
        if (isKeyDown(GLFW_KEY_A)) {
            velocity.x += -1000 * deltaTime
            animationsComponent.play("left")
        }
        if (isKeyDown(GLFW_KEY_D)) {
            velocity.x += 1000 * deltaTime
            animationsComponent.play("right")
        }
        if (isKeyDown(GLFW_KEY_W)) {
            velocity.y += 1000 * deltaTime
            animationsComponent.play("up")
        }
        if (isKeyDown(GLFW_KEY_S)) {
            velocity.y += -1000 * deltaTime
            animationsComponent.play("down")
        }
        setLinearVelocity(velocity)
        camera.position = getPosition().mul(-1f, Vector2f())
    }

    override fun onCollisionEnter(other: Collider) {
        println("Collision detected!")
    }

    override fun onCollisionExit(other: Collider) {
        println("Collision ended!")
    }
}