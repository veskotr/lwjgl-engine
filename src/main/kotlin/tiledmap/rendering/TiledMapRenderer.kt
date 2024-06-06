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
import tiledmap.tiles.Tile

class TiledMapRenderer(tiledMap: TiledMap) : Renderer<TiledMap>(parentObject = tiledMap) {

    private val squareModel = SquareModel()

    override fun render() {
        if (parentObject == null) return
        if (!parentObject!!.active) return
        bindShaderAndSetUniforms()
        bindModelBuffers()
        parentObject!!.layers.forEach { layer ->
            layer.chunks.forEach { chunk ->
                run {
                    if (camera.isChunkVisible(chunk)) {
                        chunk.tiles.forEach { row ->
                            row.forEach { tile ->
                                run {
                                    renderTile(tile)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun bindShaderAndSetUniforms() {
        shader.bind()
        shader.setUniform(PROJECTION_UNIFORM, camera.projection)
        shader.setUniform(HAS_TEXTURE_UNIFORM, 1)
        shader.setUniform(SAMPLER_UNIFORM, 0)
    }

    private fun bindModelBuffers() {
        squareModel.bindVertexBuffer()
        squareModel.bindIndexBuffer()
    }

    private fun renderTile(tile: Tile) {
        if (tile is TexturedTile) {
            defaultShader.setUniform(TRANSFORM_UNIFORM, tile.transformationMatrix)
            tile.textureAtlas.bind(0)
            squareModel.bindTextureBuffer(tile.textureBufferId)
            squareModel.drawModel()
        }
    }

}