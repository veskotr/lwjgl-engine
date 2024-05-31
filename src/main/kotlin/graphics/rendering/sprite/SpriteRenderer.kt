package graphics.rendering.sprite

import engine.camera
import graphics.rendering.Renderer
import graphics.rendering.defaultShader
import graphics.shaders.HAS_TEXTURE_UNIFORM
import graphics.shaders.PROJECTION_UNIFORM
import graphics.shaders.SAMPLER_UNIFORM
import graphics.shaders.Shader
import graphics.shaders.TRANSFORM_UNIFORM


class SpriteRenderer(override val shader: Shader = defaultShader, val sprite: Sprite) : Renderer() {
    override fun render() {
        shader.bind()
        shader.setUniform(PROJECTION_UNIFORM, camera.projection)
        shader.setUniform(TRANSFORM_UNIFORM, parentObject!!.transform.getTransformationMatrix())
        shader.setUniform(HAS_TEXTURE_UNIFORM, 1)
        shader.setUniform(SAMPLER_UNIFORM, 0)
        sprite.bindTexture(0)
        sprite.renderModel()
    }
}