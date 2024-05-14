import engine.deltaTime
import engine.structure.EngineComponent
import org.joml.Quaternionf
import org.joml.Vector2f

class SampleComponent : EngineComponent() {
    val rotation = Quaternionf()


    override fun start() {
    }

    override fun update() {
        rotation.rotateZ(Math.toRadians(90 * deltaTime).toFloat())
        parentObject!!.transform.position = Vector2f(0f, 0f)
        parentObject!!.transform.rotation = rotation
    }
}