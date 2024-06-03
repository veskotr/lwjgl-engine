package tiledmap.tiles

import org.joml.Matrix4f
import org.joml.Vector2f

abstract class Tile(val position: Vector2f, val scale: Vector2f) {

    val transformationMatrix: Matrix4f = Matrix4f().translate(position.x, position.y, 0.0f).scale(scale.x, scale.y, 1.0f)
}