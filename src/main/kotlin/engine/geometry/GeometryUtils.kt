package engine.geometry

import org.jbox2d.common.Vec2
import org.joml.Vector2f

fun Vec2.toVector2f(): Vector2f {
    return Vector2f(this.x, this.y)
}

fun Vector2f.toVec2(): Vec2 {
    return Vec2(this.x, this.y)
}
