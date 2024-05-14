package io

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.GLFW_FALSE
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFW.GLFW_RESIZABLE
import org.lwjgl.glfw.GLFW.GLFW_TRUE
import org.lwjgl.glfw.GLFW.GLFW_VISIBLE
import org.lwjgl.glfw.GLFW.glfwCreateWindow
import org.lwjgl.glfw.GLFW.glfwDefaultWindowHints
import org.lwjgl.glfw.GLFW.glfwDestroyWindow
import org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor
import org.lwjgl.glfw.GLFW.glfwGetVideoMode
import org.lwjgl.glfw.GLFW.glfwGetWindowSize
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.glfw.GLFW.glfwPollEvents
import org.lwjgl.glfw.GLFW.glfwSetErrorCallback
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowPos
import org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose
import org.lwjgl.glfw.GLFW.glfwShowWindow
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import org.lwjgl.glfw.GLFW.glfwSwapInterval
import org.lwjgl.glfw.GLFW.glfwTerminate
import org.lwjgl.glfw.GLFW.glfwWindowHint
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

var window = 0L

fun initWindow(width: Int, height: Int, title: String, fullScreen: Boolean, vsyncEnabled: Boolean) {
    GLFWErrorCallback.createPrint(System.err).set()
    glfwInit()
    glfwDefaultWindowHints()
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
    val monitor = if (fullScreen) glfwGetPrimaryMonitor() else MemoryUtil.NULL
    window = glfwCreateWindow(width, height, title, monitor, MemoryUtil.NULL)
    if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")
    glfwSetKeyCallback(window) { window, key, _, action, _ ->
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, true)
    }
    MemoryStack.stackPush().use { stack ->
        val pWidth = stack.mallocInt(1)
        val pHeight = stack.mallocInt(1)
        glfwGetWindowSize(window, pWidth, pHeight)
        val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
        if (!fullScreen) {
            glfwSetWindowPos(window, (vidmode!!.width() - pWidth[0]) / 2, (vidmode.height() - pHeight[0]) / 2)
        }
    }
    glfwMakeContextCurrent(window)
    glfwShowWindow(window)
    if (vsyncEnabled)
        glfwSwapInterval(1)
    GL.createCapabilities()
    GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
}

fun windowShouldClose() = glfwWindowShouldClose(window)

fun clearWindow() {
    glfwSwapBuffers(window)
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
}


fun pollEvents() = glfwPollEvents()

fun destroyWindow() {
    Callbacks.glfwFreeCallbacks(window)
    glfwDestroyWindow(window)
    glfwTerminate()
    glfwSetErrorCallback(null)?.free()
}