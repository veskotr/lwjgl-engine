package graphics.rendering.animations

import graphics.Texture
import graphics.rendering.SquareModel
import graphics.rendering.sprite.Sprite
import graphics.rendering.sprite.SpriteAnimationFrame
import structure.EngineComponent
import structure.EngineObject
import tiledmap.engineobjects.EngineComponentProcessor
import tiledmap.engineobjects.model.ObjectCustomProperty
import tiledmap.engineobjects.model.ObjectProperties
import tiledmap.tilesets.TileSet

class AnimationsComponentProcessor : EngineComponentProcessor {
    override fun processEngineComponent(
        engineObject: EngineObject,
        objectProperties: ObjectProperties,
        tileSets: List<TileSet>,
        path: String,
        customProperty: ObjectCustomProperty
    ): EngineComponent {
        val animations = mutableMapOf<String, Animation>()
        val textureAtlas = Texture("/levels/testScene/TX Player.png")

        val leftAnimation = Animation(
            fps = 1,
            frames = mutableSetOf(
                SpriteAnimationFrame(
                    engineObject,
                    Sprite(
                        texture = textureAtlas,
                        squareModel = SquareModel(
                            getTextureBufferIdForSprite(textureAtlas, 0, 2, 2, 4, false)
                        )
                    )
                )
            )
        )
        val rightAnimation = Animation(
            fps = 1,
            frames = mutableSetOf(
                SpriteAnimationFrame(
                    engineObject,
                    Sprite(
                        texture = textureAtlas,
                        squareModel = SquareModel(
                            getTextureBufferIdForSprite(textureAtlas, 0, 2, 2, 4, true)
                        )
                    )
                )
            )
        )

        val upAnimation = Animation(
            fps = 1,
            frames = mutableSetOf(
                SpriteAnimationFrame(
                    engineObject,
                    Sprite(
                        texture = textureAtlas,
                        squareModel = SquareModel(
                            getTextureBufferIdForSprite(textureAtlas, 0, 1, 2, 4, false)
                        )
                    )
                )
            )
        )

        val downAnimation = Animation(
            fps = 1,
            frames = mutableSetOf(
                SpriteAnimationFrame(
                    engineObject,
                    Sprite(
                        texture = textureAtlas,
                        squareModel = SquareModel(
                            getTextureBufferIdForSprite(textureAtlas, 0, 0, 2, 4, false)
                        )
                    )
                )
            )
        )

        animations.put("left", leftAnimation)
        animations.put("right", rightAnimation)
        animations.put("up", upAnimation)
        animations.put("down", downAnimation)

        return AnimationsComponent(animations)
    }

    //method that slices spritesheet and can flip the texture horizontally
    private fun getTextureBufferIdForSprite(
        textureAtlas: Texture,
        row: Int,
        column: Int,
        rows: Int,
        columns: Int,
        flipHorizontal: Boolean
    ): FloatArray {

        val width = textureAtlas.width / columns.toFloat()
        val height = textureAtlas.height / rows.toFloat()

        val glWidth = width / textureAtlas.width
        val glHeight = height / textureAtlas.height

        val textureCoordinates = if (!flipHorizontal) {
            floatArrayOf(
                column * glWidth, row * glHeight,
                column * glWidth + glWidth, row * glHeight,
                column * glWidth, row * glHeight + glHeight,
                column * glWidth + glWidth, row * glHeight + glHeight
            )
        } else {
            floatArrayOf(
                column * glWidth + glWidth, row * glHeight,
                column * glWidth, row * glHeight,
                column * glWidth + glWidth, row * glHeight + glHeight,
                column * glWidth, row * glHeight + glHeight,
            )
        }



        return textureCoordinates
    }
}