import debug.collision.CollisionDebugRenderer
import org.jbox2d.collision.shapes.Shape
import org.joml.Vector2f
import physics.Collider
import physics.createSquareShape

class TestComponent : Collider() {
    override fun createShape(): Shape {
        return createSquareShape(1f, 1f)
    }

    override fun getCollisionMesh(): List<Vector2f> {
        return listOf(
            Vector2f(0.0f, 0.0f), Vector2f(10.0f, 0.0f),
            Vector2f(10.0f, 0.0f), Vector2f(10.0f, 10.0f),
            Vector2f(10.0f, 10.0f), Vector2f(0.0f, 10.0f),
            Vector2f(0.0f, 10.0f), Vector2f(0.0f, 0.0f),
            Vector2f(10.0f, 0.0f), Vector2f(20.0f, 0.0f),
            Vector2f(20.0f, 0.0f), Vector2f(20.0f, 10.0f),
            Vector2f(20.0f, 10.0f), Vector2f(10.0f, 10.0f),
            Vector2f(10.0f, 10.0f), Vector2f(10.0f, 0.0f),
            Vector2f(0.0f, 10.0f), Vector2f(10.0f, 10.0f),
            Vector2f(10.0f, 10.0f), Vector2f(10.0f, 20.0f),
            Vector2f(10.0f, 20.0f), Vector2f(0.0f, 20.0f),
            Vector2f(0.0f, 20.0f), Vector2f(0.0f, 10.0f),
        )
    }

    override fun start() {
        debugRenderer = CollisionDebugRenderer(this)
        debugRenderer!!.parentObject = parentObject
    }

    override fun update() {
    }
}