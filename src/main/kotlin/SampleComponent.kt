import engine.deltaTime
import engine.structure.EngineComponent
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.FixtureDef
import org.jbox2d.dynamics.World
import org.joml.Quaternionf
import org.joml.Vector2f

class SampleComponent : EngineComponent() {
    val rotation = Quaternionf()
    val gravity = Vec2(0f, -10f)

    var body: Body? = null
    var world: World? = null
    val bodyDef = BodyDef()
    val shape = PolygonShape()
    val fixtureDef = FixtureDef()

    override fun start() {
        val gravity = Vec2(0f, -10f)

        world = World(gravity)

        bodyDef.type = BodyType.DYNAMIC
        bodyDef.position.set(Vec2(0f, 0f))

        body = world!!.createBody(bodyDef)

        shape.setAsBox(1f, 1f)

        fixtureDef.shape = shape
        fixtureDef.density = 1.0f
        fixtureDef.friction = 0.3f


        val fixture = body!!.createFixture(fixtureDef)
    }

    override fun update() {
        //world!!.step(1f/ deltaTime, 8, 3)
        parentObject!!.transform.position = Vector2f(body!!.position.x, body!!.position.y)
    }
}