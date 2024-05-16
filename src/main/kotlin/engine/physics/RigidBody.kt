package engine.physics

import engine.geometry.toVec2
import engine.geometry.toVector2f
import engine.structure.EngineComponent
import org.jbox2d.collision.shapes.Shape
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.Fixture
import org.jbox2d.dynamics.FixtureDef


class RigidBody(
    val density: Float = 1.0f,
    val friction: Float = 0.3f,
    val bodyType: BodyType = BodyType.DYNAMIC,
    val shape: Shape
) : EngineComponent() {
    private lateinit var body: Body
    private lateinit var fixture: Fixture

    override fun start() {
        val bodyDef = BodyDef()
        bodyDef.userData = this
        bodyDef.type = bodyType
        bodyDef.position.set(parentObject!!.transform.position.mul(SCALE_FACTOR).toVec2())
        bodyDef.angle = parentObject!!.transform.rotation
        body = createPhysicsBody(bodyDef)

        val fixtureDef = FixtureDef()
        fixtureDef.density = density
        fixtureDef.friction = friction
        fixtureDef.shape = shape
        fixture = body.createFixture(fixtureDef)
    }

    override fun update() {
        parentObject!!.transform.position = body.position.toVector2f().mul(INVERSE_SCALE_FACTOR)
        parentObject!!.transform.rotation = body.angle
    }
}