package physics

import debug.collision.CollisionDebugRenderer
import engine.DEBUG_MODE
import geometry.toVec2
import geometry.toVector2f
import org.jbox2d.collision.shapes.Shape
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.Fixture
import org.jbox2d.dynamics.FixtureDef
import org.joml.Vector2f
import structure.EngineComponent

abstract class Collider(
    offsetPosition: Vector2f = Vector2f(),
    isSensor: Boolean = true,
    bodyType: BodyType = BodyType.STATIC,
    density: Float = 1.0f,
    friction: Float = 0.3f,
) : EngineComponent() {

    private var debugRenderer: CollisionDebugRenderer? = null

    lateinit var body: Body
    lateinit var fixture: Fixture

    var offsetPosition = offsetPosition
        set(value) {
            field = value
            body.setTransform(
                (getPosition().add(value, Vector2f())).mul(SCALE_FACTOR).toVec2(),
                getRotation()
            )
        }
        get() {
            return field.mul(SCALE_FACTOR, Vector2f())
        }

    var isSensor = isSensor
        set(value) {
            field = value
            fixture.isSensor = value
        }

    var bodyType = bodyType
        set(value) {
            field = value
            body.type = value
        }

    var density = density
        set(value) {
            field = value
            fixture.density = value
        }

    var friction = friction
        set(value) {
            field = value
            fixture.friction = value
        }

    abstract fun createShape(): Shape


    override fun start() {
        if (DEBUG_MODE) {
            debugRenderer = CollisionDebugRenderer(this)
            debugRenderer!!.parentObject = parentObject
        }
        val bodyDef = BodyDef()
        bodyDef.userData = this
        bodyDef.type = bodyType
        bodyDef.position.set(getPosition().mul(SCALE_FACTOR, Vector2f()).add(offsetPosition).toVec2())
        bodyDef.angle = getRotation()
        bodyDef.fixedRotation = true
        body = createPhysicsBody(bodyDef)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = createShape()
        fixtureDef.isSensor = isSensor
        fixtureDef.density = density
        fixtureDef.friction = friction
        fixture = body.createFixture(fixtureDef)
    }

    override fun update() {
        setPosition(getColliderWorldPosition())
        setRotation(body.angle)
    }

    fun getColliderWorldPosition(): Vector2f {
        return body.position.toVector2f().sub(offsetPosition).mul(INVERSE_SCALE_FACTOR)
    }

    fun setPhysicsLinearVelocity(velocity: Vector2f) {
        body.linearVelocity = velocity.toVec2()
    }

    fun onCollisionEnter(other: Collider) {
        parentObject.getCollisionListeners().forEach { it.onCollisionEnter(other) }
    }

    fun onCollisionExit(other: Collider) {
        parentObject.getCollisionListeners().forEach { it.onCollisionExit(other) }
    }

    abstract fun getCollisionMesh(): List<Vector2f>
}