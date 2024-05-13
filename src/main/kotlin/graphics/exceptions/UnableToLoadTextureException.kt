package graphics.exceptions

private const val MESSAGE = "Failed to load a texture file!"

class UnableToLoadTextureException : RuntimeException(MESSAGE)