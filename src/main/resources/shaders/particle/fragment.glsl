#version 120

uniform sampler2D sampler;
uniform int hasTexture;
varying vec2 texCoords;
varying float alpha;

void main() {
    vec4 texture = texture2D(sampler, texCoords);
    //texture.a = texture.a * alpha;
    gl_FragColor = texture;
}
