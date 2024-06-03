package tiledmap.rendering

import engine.camera
import graphics.rendering.Renderer
import graphics.rendering.SquareModel
import graphics.rendering.defaultShader
import graphics.shaders.HAS_TEXTURE_UNIFORM
import graphics.shaders.PROJECTION_UNIFORM
import graphics.shaders.SAMPLER_UNIFORM
import graphics.shaders.TRANSFORM_UNIFORM
import tiledmap.TiledMap
import tiledmap.tiles.TexturedTile

class TiledMapRenderer(tiledMap: TiledMap) : Renderer<TiledMap>(parentObject = tiledMap) {

    private val squareModel = SquareModel()

    override fun render() {
        shader.bind()
        shader.setUniform(PROJECTION_UNIFORM, camera.projection)
        shader.setUniform(HAS_TEXTURE_UNIFORM, 1)
        shader.setUniform(SAMPLER_UNIFORM, 0)
        squareModel.bindVertexBuffer()
        squareModel.bindIndexBuffer()
        parentObject!!.layers.forEach { layer ->
            layer.chunks.forEach { chunk ->
                chunk.tiles.forEach { row ->
                    row.forEach { tile ->
                        run {
                            if (tile is TexturedTile) {
                                defaultShader.setUniform(TRANSFORM_UNIFORM, tile.transformationMatrix)
                                tile.textureAtlas.bind(0)
                                squareModel.bindTextureBuffer(tile.textureBufferId)
                                squareModel.drawModel()
                            }
                        }
                    }
                }
            }
        }
    }
}