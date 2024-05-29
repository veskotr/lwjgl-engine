package graphics.particles

import engine.deltaTime
import graphics.rendering.sprite.Sprite
import org.joml.Vector2f
import structure.EngineComponent
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class ParticleEmitter(
    val sprite: Sprite,
    private val relativePosition: Vector2f = Vector2f(),
    private val maxVelocity: Float = 100f,
    private val minVelocity: Float = 0f,
    private val minSize: Float = 32f,
    private val maxSize: Float = 32f,
    private val minEnergy: Int = 1,
    private val maxEnergy: Int = 1,
    private val minEmission: Int = 1,
    private val maxEmission: Int = 1,
    private val useWorldSpace: Boolean = false,
    private val emissionInterval: Float = 0.5f
) : EngineComponent() {

    var particles = mutableListOf<Particle>()

    private lateinit var particleRenderer: ParticleRenderer
    private var timeSinceLastEmission = 0f


    override fun start() {
        particleRenderer = ParticleRenderer(particleEmitter = this)
    }

    override fun update() {
        timeSinceLastEmission += deltaTime

        if (timeSinceLastEmission >= emissionInterval) {
            emitParticles()
            timeSinceLastEmission = 0f
        }

        particles = particles.filter { it.alive }.toMutableList()
        particles.forEach { it.update() }
        Vector2f().absolute()
    }

    private fun emitParticles() {
        val emissionCount = Random.nextInt(minEmission, maxEmission + 1)
        repeat(emissionCount) {
            particles.add(
                Particle(
                    randomSize(),
                    getPosition().add(relativePosition, Vector2f()),
                    randomVelocity(),
                    randomEnergy(),
                    useWorldSpace
                )
            )
        }
    }


    private fun randomSize(): Float {
        return Random.nextFloat() * (maxSize - minSize) + minSize
    }

    private fun randomEnergy(): Int {
        return Random.nextInt(minEnergy, maxEnergy + 1)
    }

    private fun randomVelocity(): Vector2f {
        val angle = Random.nextFloat() * 2 * Math.PI
        val velocityMagnitude = Random.nextFloat() * (maxVelocity - minVelocity) + minVelocity
        return Vector2f(
            (cos(angle) * velocityMagnitude).toFloat(),
            (sin(angle) * velocityMagnitude).toFloat()
        )
    }
}