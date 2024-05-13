package graphics.utils

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15
import java.util.function.Consumer

private val vbos: MutableList<Int> = ArrayList()

fun createVbo(data: FloatArray): Int {
    val bufferId = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId)
    val floatBuffer = BufferUtils.createFloatBuffer(data.size)
    floatBuffer.put(data)
    floatBuffer.position(0)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW)
    vbos.add(bufferId)
    return bufferId
}

fun createVbo(data: IntArray): Int {
    val bufferId = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, bufferId)
    val intBuffer = BufferUtils.createIntBuffer(data.size)
    intBuffer.put(data)
    intBuffer.position(0)
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL15.GL_STATIC_DRAW)
    vbos.add(bufferId)
    return bufferId
}

fun cleanupGraphics() {
    vbos.forEach(Consumer { buffer: Int? -> GL15.glDeleteBuffers(buffer!!) })
}