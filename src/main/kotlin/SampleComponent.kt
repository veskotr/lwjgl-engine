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
        parentObject!!.transform.rotation = rotation
    }
}