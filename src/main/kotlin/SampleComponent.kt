import engine.deltaTime
import engine.structure.EngineComponent
import org.joml.Quaternionf
import org.joml.Vector2f

class SampleComponent : EngineComponent() {


    override fun start() {
        parentObject!!.transform.scale(Vector2f(1f, 2f))
    }

    override fun update() {
        val rotation = Quaternionf()
        rotation.rotateZ(Math.toRadians(90 * deltaTime).toFloat())
        parentObject!!.transform.rotate(rotation)
    }
}