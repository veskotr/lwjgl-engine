package graphics.rendering

import graphics.utils.createVbo
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL15.glBindBuffer
import org.lwjgl.opengl.GL20.*


/**
 * Created by vesko on 01.07.19.
 */
class SquareModel : Model {


    companion object {
        private val vertices = floatArrayOf(
            -0.5f,  0.5f,  // top left corner
            0.5f,  0.5f,  // top right corner
            -0.5f, -0.5f,  // bottom left corner
            0.5f, -0.5f   // bottom right corner
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

    constructor() : this(textures)

    constructor(textures: FloatArray) {
        fillBuffers(textures)
    }

    private fun fillBuffers(texCoords: FloatArray) {
        count = indices.size
        tId = createVbo(texCoords)
        vId = createVbo(vertices)
        iId = createVbo(indices)
    }

    override fun bindModel() {
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glBindBuffer(GL_ARRAY_BUFFER, vId)
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, tId)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iId)
    }

    override fun render() {
        bindModel()
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0)
        unbindModel()
    }

    override fun unbindModel() {
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }
}