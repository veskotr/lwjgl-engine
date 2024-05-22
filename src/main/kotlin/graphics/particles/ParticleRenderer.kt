package graphics.particles

import graphics.rendering.Renderer
import graphics.rendering.SquareModel
import graphics.rendering.particleShader
import graphics.shaders.PROJECTION_UNIFORM
import graphics.shaders.SAMPLER_UNIFORM
import graphics.shaders.TRANSFORM_UNIFORM
import io.getCameraProjectionMatrix

class ParticleRenderer(private val particleEmitter: ParticleEmitter) :
    Renderer(particleShader, particleEmitter.parentObject) {

    private val squareModel = SquareModel()

    override fun render() {
        shader.bind()
        shader.setUniform(PROJECTION_UNIFORM, getCameraProjectionMatrix())
        shader.setUniform(SAMPLER_UNIFORM, 0)
        particleEmitter.sprite.bindTexture(0)
        particleEmitter.particles.forEach {
            shader.setUniform(TRANSFORM_UNIFORM, it.matrix)
            squareModel.render()
        }
    }
}