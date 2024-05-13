package graphics

import graphics.exceptions.UnableToLoadTextureException
import io.loadResource
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.stb.STBImage
import org.lwjgl.stb.STBImage.stbi_load_from_memory
import org.lwjgl.system.MemoryStack
import java.io.IOException
import java.nio.ByteBuffer

class Texture {
    private var textureId: Int = 0
    private var width: Int = 0
    private var height: Int = 0
    private var buffer: ByteBuffer? = null


    constructor(textureId: Int, width: Int, height: Int) {
        this.textureId = textureId
        this.width = width
        this.height = height
    }

    constructor(fileName: String) {
        loadTexture(fileName)
    }

    /**
     * binds texture to texture sampler
     *
     * @param sampler the id ot the sampler
     */
    fun bind(sampler: Int) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + sampler)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId)
    }

    private fun loadTexture(fileName: String) {
        try {
            MemoryStack.stackPush().use { stack ->
                /* Prepare image buffers */
                val w = stack.mallocInt(1)
                val h = stack.mallocInt(1)
                val comp = stack.mallocInt(1)

                /* Load image */
                STBImage.stbi_set_flip_vertically_on_load(false)
                buffer = stbi_load_from_memory(loadResource(fileName), w, h, comp, 4)
                if (buffer == null) {
                    throw RuntimeException(
                        "Failed to load a texture file!"
                                + System.lineSeparator() + STBImage.stbi_failure_reason()
                    )
                }
                /* Get width and height of image */
                width = w.get()
                height = h.get()
                textureId = GL11.glGenTextures()
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId)
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)
                GL11.glTexImage2D(
                    GL11.GL_TEXTURE_2D,
                    0,
                    GL11.GL_RGBA,
                    width,
                    height,
                    0,
                    GL11.GL_RGBA,
                    GL11.GL_UNSIGNED_BYTE,
                    buffer
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw UnableToLoadTextureException()
        }
    }
}
