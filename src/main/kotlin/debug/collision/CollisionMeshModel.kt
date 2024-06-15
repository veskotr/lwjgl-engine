package debug.collision

import graphics.rendering.Model
import graphics.utils.createVbo
import org.joml.Vector2f
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20

class CollisionMeshModel(verticesList: List<Vector2f>) : Model() {

    init {
        val vertices = verticesList.flatMap { listOf(it.x, it.y) }.toFloatArray()
        val indices = List(verticesList.size) { index -> index }.toIntArray()
        count = indices.size
        vId = createVbo(vertices)
        iId = createVbo(indices)
        tId = 0
    }

    override fun bindVertexBuffer(vId: Int) {
        GL20.glEnableVertexAttribArray(0)
        GL15.glBindBuffer(GL20.GL_ARRAY_BUFFER, vId)
        GL20.glVertexAttribPointer(0, 2, GL20.GL_FLOAT, false, 0, 0)
    }

    override fun bindTextureBuffer(tId: Int) {
        GL15.glBindBuffer(GL20.GL_ARRAY_BUFFER, tId)
    }

    override fun bindIndexBuffer() {
        GL15.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, iId)
    }

    override fun drawModel() {
        GL15.glDrawElements(GL20.GL_LINE_LOOP, count, GL20.GL_UNSIGNED_INT, 0)
    }

    override fun unbindModel() {
        GL15.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0)
        GL15.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, 0)
    }
}