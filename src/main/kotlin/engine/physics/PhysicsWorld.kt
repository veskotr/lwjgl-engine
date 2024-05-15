package engine.physics


import engine.updateEngineObjects
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.World
import org.joml.Vector2f

private val world = World(Vec2(0f, -10f))

private val physicsActive = true

fun setWorldGravity(gravity: Vector2f) {
    world.gravity = Vec2(gravity.x, gravity.y)
}

fun createPhysicsBody(bodyDef: BodyDef): Body {
    return world.createBody(bodyDef)
}

fun createSquareShape(width: Float, height: Float): PolygonShape {
    val shape = PolygonShape()
    shape.setAsBox(width, height)
    return shape
}

fun updatePhysics() {
    if (physicsActive) {
        world.step(1 / 60f, 8, 3)
        updateEngineObjects()
    }
}