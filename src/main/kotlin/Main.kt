import engine.initGame
import engine.runGame
import engine.startGame
import graphics.Texture
import graphics.particles.ParticleEmitter
import graphics.rendering.sprite.SpriteRenderer
import graphics.rendering.sprite.createSprite
import org.jbox2d.dynamics.BodyType
import org.joml.Vector2f
import physics.BoxCollider
import physics.setWorldGravity
import structure.EngineObject
import tiledmap.createTiledMapFromFile


fun main() {
    initGame(
        windowWidth = 1920,
        windowHeight = 980,
        windowTitle = "Test Game",
        fullScreen = false,
        vsyncEnabled = false
    )

    setWorldGravity(Vector2f(0f, 0f))

    val map = createTiledMapFromFile("/levels/testScene")

    startGame()

    runGame()
}