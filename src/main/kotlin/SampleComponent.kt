import engine.camera
import engine.deltaTime
import graphics.Texture
import graphics.rendering.SquareModel
import graphics.rendering.defaultShader
import graphics.shaders.HAS_TEXTURE_UNIFORM
import graphics.shaders.PROJECTION_UNIFORM
import graphics.shaders.SAMPLER_UNIFORM
import graphics.shaders.TRANSFORM_UNIFORM
import io.isKeyDown
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*
import physics.Collider
import physics.ICollisionListener
import structure.EngineComponent
import tiledmap.createTiledMapFromFile
import tiledmap.tiles.TexturedTile

class SampleComponent : EngineComponent(), ICollisionListener {

    val map = createTiledMapFromFile("/levels/testScene")
    val squareModel = SquareModel()
    val texture = Texture("/tile2.png")

    override fun start() {
        camera.scale = Vector2f(1f)
    }

    override fun update() {
        if (isKeyDown(GLFW_KEY_A)) {
            camera.position.x += 1000 * deltaTime
        }
        if (isKeyDown(GLFW_KEY_D)) {
            camera.position.x -= 1000 * deltaTime
        }
        if (isKeyDown(GLFW_KEY_W)) {
            camera.position.y -= 1000 * deltaTime
        }
        if (isKeyDown(GLFW_KEY_S)) {
            camera.position.y += 1000 * deltaTime
        }

        defaultShader.bind()
        defaultShader.setUniform(PROJECTION_UNIFORM, camera.projection)
        defaultShader.setUniform(HAS_TEXTURE_UNIFORM, 1)
        defaultShader.setUniform(SAMPLER_UNIFORM, 0)
        squareModel.bindVertexBuffer()
        squareModel.bindIndexBuffer()

        map.layers.forEach { layer ->
            layer.chunks.forEach { chunk ->
                chunk.tiles.forEach { row ->
                    row.forEach { tile ->
                        run {
                            if (tile is TexturedTile) {
                                val matrix = Matrix4f().identity()
                                matrix.setTranslation(Vector3f(tile.position, 0.0f))
                                matrix.scale(Vector3f(tile.scale, 1.0f))
                                defaultShader.setUniform(TRANSFORM_UNIFORM, matrix)
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

    override fun onCollisionEnter(other: Collider) {
        println("Collision detected!")
    }

    override fun onCollisionExit(other: Collider) {
        println("Collision ended!")
    }
}