package graphics.shaders

import org.lwjgl.opengl.GL20.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.system.exitProcess

const val PROJECTION_UNIFORM = "projection"
const val TRANSFORM_UNIFORM = "model"
const val HAS_TEXTURE_UNIFORM = "hasTexture"
const val SAMPLER_UNIFORM = "sampler"

fun createShader(vertexCode: String, fragmentCode: String): Shader {
    val program = glCreateProgram()
    val vs = glCreateShader(GL_VERTEX_SHADER)
    glShaderSource(vs, vertexCode)
    glCompileShader(vs)
    if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
        System.err.println("Vertex error " + glGetShaderInfoLog(vs))
        exitProcess(1)
    }
    val fs = glCreateShader(GL_FRAGMENT_SHADER)
    glShaderSource(fs, fragmentCode)
    glCompileShader(fs)
    if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
        System.err.println("Fragment error " + glGetShaderInfoLog(fs))
        exitProcess(1)
    }
    glAttachShader(program, vs)
    glAttachShader(program, fs)
    glLinkProgram(program)
    if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
        System.err.println("Link error " + glGetProgramInfoLog(program))
        exitProcess(1)
    }
    glValidateProgram(program)
    if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
        System.err.println("Validate error " + glGetProgramInfoLog(program))
        exitProcess(1)
    }

    return Shader(program)
}

fun loadShaderCode(relativeFilePath: String): String {
    val reader: BufferedReader
    val output = StringBuilder()
    val inputStream: InputStream? = object {}.javaClass.getResourceAsStream(relativeFilePath)
    try {
        reader = BufferedReader(InputStreamReader(inputStream!!))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            output.append(line).append("\n")
        }
        reader.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return output.toString()
}

fun createBasicShader(): Shader {
    return createShader(loadShaderCode("/shaders/default/vertex.glsl"), loadShaderCode("/shaders/default/fragment.glsl"))
}

fun createParticleShader(): Shader {
    return createShader(loadShaderCode("/shaders/particle/vertex.glsl"), loadShaderCode("/shaders/particle/fragment.glsl"))
}