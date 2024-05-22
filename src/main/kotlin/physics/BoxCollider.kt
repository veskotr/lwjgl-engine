package physics

import org.jbox2d.collision.shapes.Shape
import org.jbox2d.dynamics.BodyType
import org.joml.Vector2f

class BoxCollider(
    val size: Vector2f,
    offsetPosition: Vector2f = Vector2f(),
    isSensor: Boolean = true,
    bodyType: BodyType = BodyType.STATIC,
    density: Float = 1.0f,
    friction: Float = 0.3f,
) : Collider(offsetPosition, isSensor, bodyType, density, friction) {

    override fun createShape(): Shape {
        return createSquareShape(size.x, size.y)
    }
}