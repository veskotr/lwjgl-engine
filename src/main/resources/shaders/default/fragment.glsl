#version 120

uniform sampler2D sampler;
uniform int hasTexture;
uniform vec4 color;
varying vec2 texCoords;

void main() {
    vec4 texture;
    if (hasTexture == 1){
        texture = texture2D(sampler, texCoords);
    }else {
        texture = color;
    }
    gl_FragColor = texture;
}
