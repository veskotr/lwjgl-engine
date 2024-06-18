package graphics.rendering.sprite

import graphics.Texture
import graphics.rendering.SquareModel
import org.joml.Vector2f

val defaultSquareModel = SquareModel(size = Vector2f(1.0f))

fun createSprite(texture: Texture): Sprite {
    return Sprite(texture, defaultSquareModel)
}

fun sliceSpriteSheet(texture: Texture, spriteSize: Vector2f, tileSize: Vector2f): List<Sprite> {
    val glWidth: Float = spriteSize.x / texture.width
    val glHeight: Float = spriteSize.y / texture.height

    val columnCount = (texture.width / spriteSize.x).toInt()
    val rowCount = (texture.height / spriteSize.y).toInt()

    val sprites: MutableList<Sprite> = ArrayList()

    for (i in 0 until rowCount) {
        for (j in 0 until columnCount) {
            val textureCoordinates = floatArrayOf(
                j * glWidth, i * glHeight,
                j * glWidth + glWidth, i * glHeight,
                j * glWidth + glWidth, i * glHeight + glHeight,
                j * glWidth, i * glHeight + glHeight
            )
            sprites.add(Sprite(texture, SquareModel(textureCoordinates, tileSize)))
        }
    }

    return sprites
}
