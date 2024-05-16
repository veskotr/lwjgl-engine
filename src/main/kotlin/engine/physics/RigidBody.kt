package engine.physics

import engine.geometry.toVector2
import engine.geometry.toVector2f
import engine.structure.EngineComponent
import org.dyn4j.dynamics.Body
import org.dyn4j.geometry.Convex
import org.dyn4j.geometry.Mass
import org.dyn4j.geometry.MassType

class RigidBody(
    val fixture: Convex,
    val static: Boolean = false
) : EngineComponent() {
    private val body: Body = Body()

    override fun start() {
        body.addFixture(fixture)
        body.transform.rotation.rotate(parentObject!!.transform.rotation.toDouble())
        body.transform.translation = parentObject!!.transform.position.toVector2()
        body.setMass(MassType.NORMAL)
        if (static) {
            body.setMass(MassType.INFINITE)
        }
        addPhysicsBody(body)
    }

    override fun update() {
        parentObject!!.transform.rotation = body.transform.rotation.toDegrees().toFloat()
        parentObject!!.transform.position = body.transform.translation.toVector2f()
    }
}