package graphics.shaders

import org.lwjgl.opengl.GL20.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.system.exitProcess

private const val vertexCode = "#version 120\n" +
        "\n" +
        "attribute vec2 textures;\n" +
        "attribute vec2 vertices;\n" +
        "\n" +
        "varying vec2 texCoords;\n" +
        "\n" +
        "uniform mat4 projection;\n" +
        "\n" +
        "uniform mat4 model;\n" +
        "\n" +
        "void main() {\n" +
        "\n" +
        "    texCoords = textures;\n" +
        "\n" +
        "    gl_Position = projection * model * vec4(vertices,0,1);\n" +
        "}\n"

private const val fragmentCode = "#version 120\n" +
        "\n" +
        "uniform sampler2D sampler;\n" +
        "uniform int hasTexture;\n" +
        "uniform vec4 color;\n" +
        "varying vec2 texCoords;\n" +
        "\n" +
        "void main() {\n" +
        "    vec4 texture;\n" +
        "    if (hasTexture == 1){\n" +
        "        texture = texture2D(sampler, texCoords);\n" +
        "        //texture *= color;\n" +
        "    }else {\n" +
        "        texture = color;\n" +
        "    }\n" +
        "    gl_FragColor = texture;\n" +
        "}"

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