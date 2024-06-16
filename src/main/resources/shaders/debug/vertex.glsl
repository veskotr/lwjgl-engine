#version 120

attribute vec2 vertices;

uniform mat4 projection;

uniform mat4 model;

void main() {
    gl_Position = projection * model * vec4(vertices,0,1);
}
