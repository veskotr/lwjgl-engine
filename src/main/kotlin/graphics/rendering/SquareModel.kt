package graphics.rendering

import graphics.utils.createVbo
import org.joml.Vector2f
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL15.glBindBuffer
import org.lwjgl.opengl.GL20.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL20.GL_ELEMENT_ARRAY_BUFFER
import org.lwjgl.opengl.GL20.GL_FLOAT
import org.lwjgl.opengl.GL20.GL_TRIANGLES
import org.lwjgl.opengl.GL20.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer


/**
 * Created by vesko on 01.07.19.
 */
class SquareModel : Model {


    companion object {
        private val vertices = floatArrayOf(
            -1f, 1f,  // top left corner
            1f, 1f,  // top right corner
            -1f, -1f,  // bottom left corner
            1f, -1f   // bottom right corner
        )

        private val textures = floatArrayOf(
            0f, 0f,  // bottom left corner of the texture
            1f, 0f,  // bottom right corner of the texture
            0f, 1f,  // top left corner of the texture
            1f, 1f   // top right corner of the texture
        )

        private val indices = intArrayOf(
            0, 1, 2,  // first triangle (top left - top right - bottom left)
            1, 3, 2   // second triangle (top right - bottom right - bottom left)
        )
    }

    constructor(size: Vector2f) : this(textures, size)

    constructor(textures: FloatArray, size: Vector2f) {
        fillBuffers(textures, size)
    }

    constructor(textureBufferId: Int, size: Vector2f) {
        tId = textureBufferId
        fillVertexBuffer(size)
    }

    private fun fillVertexBuffer(size: Vector2f) {
        count = indices.size
        val scaledVertices = vertices.mapIndexed { index, value ->
            if (index % 2 == 0) {
                value * size.x
            } else {
                value * size.y
            }
        }.toFloatArray()
        vId = createVbo(scaledVertices)
        iId = createVbo(indices)
    }

    private fun fillBuffers(texCoords: FloatArray, size: Vector2f) {
        count = indices.size
        tId = createVbo(texCoords)
       fillVertexBuffer(size)
    }

    override fun bindVertexBuffer(vId: Int) {
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glBindBuffer(GL_ARRAY_BUFFER, vId)
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0)
    }

    override fun bindTextureBuffer(tId: Int) {
        glBindBuffer(GL_ARRAY_BUFFER, tId)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0)
    }

    override fun bindIndexBuffer() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId)
    }

    override fun drawModel() {
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0)
    }

    override fun unbindModel() {
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }
}