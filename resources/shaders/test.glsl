#type vertex
#version 330 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec4 a_color;

uniform vec2 screen;

out vec4 f_color;

void main(){
    f_color = a_color;
    gl_Position = vec4(position.x / screen.x, position.y / screen.y, 0, 1);
}

#type fragment
#version 330 core

in vec4 f_color;

out vec4 color;

void main(){
    color = f_color;
}