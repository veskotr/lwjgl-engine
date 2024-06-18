package graphics.particles

import engine.camera
import graphics.rendering.AbstractRenderer
import graphics.rendering.SquareModel
import graphics.rendering.particleShader
import graphics.shaders.PROJECTION_UNIFORM
import graphics.shaders.SAMPLER_UNIFORM
import graphics.shaders.TRANSFORM_UNIFORM
import org.joml.Vector2f

class ParticleRenderer(private val particleEmitter: ParticleEmitter) :
    AbstractRenderer(particleShader, particleEmitter.parentObject, layerName = particleEmitter.parentObject.layerName) {

    private val squareModel = SquareModel(Vector2f(1.0f))

    companion object {
        const val ALPHA_UNIFORM = "alpha"
    }

    override fun render() {
        shader.bind()
        shader.setUniform(PROJECTION_UNIFORM, camera.projection)
        shader.setUniform(SAMPLER_UNIFORM, 0)
        particleEmitter.sprite.bindTexture(0)
        particleEmitter.particles.forEach {
            shader.setUniform(TRANSFORM_UNIFORM, it.matrix)
            shader.setUniform(ALPHA_UNIFORM, 1f - it.aliveTime.toFloat() / it.life.toFloat())
            squareModel.render()
        }
    }
}