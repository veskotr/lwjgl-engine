package engine.physics

import engine.structure.EngineComponent
import org.jbox2d.collision.shapes.Shape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.Fixture
import org.jbox2d.dynamics.FixtureDef
import org.joml.Vector2f

class RigidBody(
    val density: Float = 1.0f,
    val friction: Float = 1.0f,
    val bodyType: BodyType = BodyType.DYNAMIC,
    val shape: Shape
) : EngineComponent() {
    private lateinit var body: Body
    private lateinit var fixture: Fixture

    init {

    }

    override fun start() {
        val bodyDef = BodyDef()
        bodyDef.type = bodyType
        bodyDef.position.set(parentObject!!.transform.position.x, parentObject!!.transform.position.y)
        body = createPhysicsBody(bodyDef)

        val fixtureDef = FixtureDef()
        fixtureDef.density = density
        fixtureDef.friction = friction
        fixtureDef.shape = shape

        fixture = body.createFixture(fixtureDef)
    }

    override fun update() {
        parentObject!!.transform.position = Vector2f(body.position.x, body.position.y)
        parentObject!!.transform.rotation = body.angle
    }
}