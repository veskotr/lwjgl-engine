package graphics.rendering

import graphics.utils.createVbo
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL15.glBindBuffer
import org.lwjgl.opengl.GL20.*


/**
 * Created by vesko on 01.07.19.
 */
class SquareModel {
    private var vId = 0
    private var tId = 0
    private var iId = 0
    private var count = 0

    companion object {
        private val vertices = floatArrayOf(
            -1f, 1f,  // top left corner
            1f, 1f,  // top right corner
            -1f, -1f,  // bottom left corner
            1f, -1f
        )
        private val textures = floatArrayOf(
            0f, 0f,
            1f, 0f,
            0f, 1f,
            1f, 1f
        )
        private val indices = intArrayOf(
            1, 0, 2,  // first triangle (bottom left - top left - top right)
            1, 2, 3
        )
    }

    constructor() {
        fillBuffers(textures)
    }

    constructor(textures: FloatArray) {

        fillBuffers(textures)
    }

    private fun fillBuffers(texCoords: FloatArray) {
        count = indices.size
        tId = createVbo(texCoords)
        vId = createVbo(vertices)
        iId = createVbo(indices)
    }

    fun render() {
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glBindBuffer(GL_ARRAY_BUFFER, vId)
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, tId)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId)
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
    }
}