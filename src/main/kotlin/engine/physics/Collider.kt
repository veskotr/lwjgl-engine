package engine.physics

import engine.geometry.toVec2
import engine.structure.EngineComponent
import org.jbox2d.collision.shapes.Shape
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.Fixture
import org.jbox2d.dynamics.FixtureDef

class Collider(
    val shape: Shape
) : EngineComponent() {

    private lateinit var body: Body
    private lateinit var fixture: Fixture

    override fun start() {
        val bodyDef = BodyDef()
        bodyDef.userData = this
        bodyDef.type = BodyType.KINEMATIC
        bodyDef.position.set(parentObject!!.transform.position.mul(SCALE_FACTOR).toVec2())
        bodyDef.angle = parentObject!!.transform.rotation
        body = createPhysicsBody(bodyDef)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.isSensor = true
        fixture = body.createFixture(fixtureDef)
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    fun onCollisionEnter(other: Collider) {
        println("Collision Enter")
    }

    fun onCollisionExit(other: Collider) {
        println("Collision Exit")
    }

}