package io

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

var window = 0L

fun initWindow(width: Int, height: Int, title: String) {
    GLFWErrorCallback.createPrint(System.err).set()
    check(GLFW.glfwInit()) { "Unable to initialize GLFW" }
    GLFW.glfwDefaultWindowHints()
    GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
    GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)
    window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
    if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")
    GLFW.glfwSetKeyCallback(window) { window, key, _, action, _ ->
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) GLFW.glfwSetWindowShouldClose(window, true)
    }
    MemoryStack.stackPush().use { stack ->
        val pWidth = stack.mallocInt(1)
        val pHeight = stack.mallocInt(1)
        GLFW.glfwGetWindowSize(window, pWidth, pHeight)
        val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
        GLFW.glfwSetWindowPos(window, (vidmode!!.width() - pWidth[0]) / 2, (vidmode.height() - pHeight[0]) / 2)
    }
    GLFW.glfwMakeContextCurrent(window)
    GLFW.glfwSwapInterval(1)
    GLFW.glfwShowWindow(window)
    GL.createCapabilities()
    GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
}

fun windowShouldClose() = GLFW.glfwWindowShouldClose(window)

fun clearWindow() {
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
    GLFW.glfwSwapBuffers(window)
}

fun pollEvents() = GLFW.glfwPollEvents()

fun destroyWindow() {
    Callbacks.glfwFreeCallbacks(window)
    GLFW.glfwDestroyWindow(window)
    GLFW.glfwTerminate()
    GLFW.glfwSetErrorCallback(null)?.free()
}