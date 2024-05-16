package engine.geometry

import org.dyn4j.geometry.Vector2
import org.joml.Vector2f

fun Vector2.toVector2f(): Vector2f {
    return Vector2f(this.x.toFloat(), this.y.toFloat())
}

fun Vector2f.toVector2(): Vector2 {
    return Vector2(this.x.toDouble(), this.y.toDouble())
}