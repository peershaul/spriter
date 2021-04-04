#type vertex
#version 330 core

layout (location = 0) in vec2 pos;
layout (location = 1) in vec2 a_texCoords;

uniform vec2 screen;

out vec2 f_texCoords;

void main(){
    gl_Position = vec4(2 * pos / screen - vec2(1), 0, 1);
    f_texCoords = a_texCoords;
}

#type fragment
#version 330 core

in vec2 f_texCoords;

uniform sampler2D Texture;

out vec4 color;

void main(){
    color = texture(Texture, f_texCoords);
}

