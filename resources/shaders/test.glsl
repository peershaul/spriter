#type vertex
#version 330 core

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 texCoords;

uniform mat4 projection;
uniform mat4 viewMat;

out vec4 fcolor;
out vec2 fTexCoords;


void main(){
    fcolor = vec4(1, 1, 1, 1);
    fTexCoords = texCoords;
    gl_Position = projection * viewMat * position;
}

#type fragment
#version 330 core

uniform sampler2D Texture;

in vec4 fcolor;
in vec2 fTexCoords;

out vec4 color;

void main(){
    color = texture(Texture, fTexCoords);
}