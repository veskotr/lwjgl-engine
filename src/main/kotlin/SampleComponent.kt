import engine.physics.physicsDeltaTime
import engine.structure.EngineComponent
import org.dyn4j.dynamics.Body
import org.dyn4j.geometry.Geometry
import org.dyn4j.geometry.MassType
import org.dyn4j.geometry.Vector2
import org.dyn4j.world.World
import org.joml.Vector2f

class SampleComponent : EngineComponent() {

    val world = World<Body>()
    val body = Body()

    override fun start() {
        world.gravity = Vector2(0.0, -100.0)
        body.addFixture(Geometry.createSquare(32.0))
        body.translate(parentObject!!.transform.position.x.toDouble(), parentObject!!.transform.position.y.toDouble())
        body.setMass(MassType.NORMAL)
        world.addBody(body)
    }

    override fun update() {
        world.update(physicsDeltaTime.toDouble())
        parentObject!!.transform.position =
            Vector2f(body.transform.translationX.toFloat(), body.transform.translationY.toFloat())
    }
}