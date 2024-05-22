package graphics.particles

import graphics.rendering.sprite.Sprite
import org.joml.Vector2f
import structure.EngineComponent

class ParticleEmitter(
    val sprite: Sprite,
    private val relativePosition: Vector2f = Vector2f(),
    private val velocity: Vector2f,
    private val minSize: Float = 32f,
    private val maxSize: Float = 32f,
    private val minEnergy: Int = 1,
    private val maxEnergy: Int = 1,
    private val minEmission: Int = 1,
    private val maxEmission: Int = 1,
    private val useWorldSpace: Boolean = false
) : EngineComponent() {

    var particles = mutableListOf<Particle>()

    private lateinit var particleRenderer: ParticleRenderer

    override fun start() {
        particleRenderer = ParticleRenderer(particleEmitter = this)
    }

    override fun update() {
        particles.add(
            Particle(
                maxSize,
                getPosition().add(relativePosition, Vector2f()),
                velocity,
                maxEnergy,
                useWorldSpace
            )
        )
        particles = particles.filter { it.alive }.toMutableList()
        particles.forEach { it.update(getPosition().add(relativePosition, Vector2f())) }
    }
}