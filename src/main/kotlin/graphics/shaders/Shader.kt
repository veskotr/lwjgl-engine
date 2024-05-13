package graphics.shaders

import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import java.nio.FloatBuffer

class Shader(
    private val program: Int,

    private val matrixData: FloatBuffer = BufferUtils.createFloatBuffer(16)
) {

    fun bindAttributeLocation(id: Int, name: String) {
        glBindAttribLocation(program, id, name)
    }

    fun setUniform(name: String, value: Int) {
        val location = glGetUniformLocation(program, name)
        if (location != -1) glUniform1i(location, value)
    }

    fun setUniform(name: String, value: Float) {
        val location = glGetUniformLocation(program, name)
        if (location != -1) glUniform1f(location, value)
    }

    fun setUniform(name: String, value: Matrix4f) {
        value[matrixData]
        val location = glGetUniformLocation(program, name)
        if (location != -1) {
            glUniformMatrix4fv(location, false, matrixData)
        }
    }

    fun setUniform(name: String, value: Vector3f) {
        val location = glGetUniformLocation(program, name)
        if (location != -1) {
            glUniform3f(location, value.x, value.y, value.z)
        }
    }

    fun setUniform(name: String, value: Vector2f) {
        val location = glGetUniformLocation(program, name)
        if (location != -1) {
            glUniform2f(location, value.x, value.y)
        }
    }

    fun setUniform(name: String, value: Vector4f) {
        val location = glGetUniformLocation(program, name)
        if (location != -1) {
            glUniform4f(location, value.x, value.y, value.z, value.w)
        }
    }

    fun setUniform(name: String, values: FloatArray) {
        val location = glGetUniformLocation(program, name)
        if (location != -1) {
            glUniform2fv(location, values)
        }
    }

    fun setUniform(name: String, values: List<Vector3f>) {
        val location = glGetUniformLocation(program, name)
        if (location != -1) {
            val data = FloatArray(values.size * 2)
            var i = 1
            while (i < values.size) {
                data[i - 1] = values[i - 1].x
                data[i] = values[i - 1].y
                i += 2
            }
            glUniform2fv(location, data)
        }
    }

    fun bind() {
        glUseProgram(program)
    }
}