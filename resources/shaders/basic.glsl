#type vertex
#version 330 core

layout (location = 0) in vec2 position;

uniform vec2 screen;

void main(){
    gl_Position = vec4(position / screen, 0, 1);
}


#type fragment
#version 330 core

uniform vec3 Color;

out vec4 color;

void main(){
    color = vec4(Color, 1);
}