package io

import org.apache.commons.io.IOUtils
import org.lwjgl.BufferUtils
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer

fun loadResource(filename: String): ByteBuffer {
    val bytes: ByteArray = IOUtils.toByteArray(object {}.javaClass.getResourceAsStream(filename))
    val byteBuffer = BufferUtils.createByteBuffer(bytes.size)
    byteBuffer.put(bytes)
    byteBuffer.position(0)
    return byteBuffer
}

fun loadFile(filename: String): ByteBuffer {
    val bytes: ByteArray = IOUtils.toByteArray(FileInputStream(File(filename)))
    val byteBuffer = BufferUtils.createByteBuffer(bytes.size)
    byteBuffer.put(bytes)
    byteBuffer.position(0)
    return byteBuffer
}