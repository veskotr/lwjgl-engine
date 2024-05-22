#version 120

attribute vec2 vertices;
attribute vec2 textures;

varying vec2 texCoords;

uniform mat4 projection;

uniform mat4 model;

void main() {

    texCoords = textures;

    gl_Position = projection * model * vec4(vertices,0,1);
}
