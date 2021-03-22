#type vertex
#version 330 core

layout(location = 0) in vec4 pos;
layout(location = 1) in vec4 v_color;

uniform vec2 screen;

out vec4 f_color;

void main(){
    vec4 position = vec4(pos.x / screen.x, pos.y / screen.y, pos.zw);
    gl_Position = position;
    f_color = v_color;
}

#type fragment
#version 330 core

in vec4 f_color;

out vec4 color;

void main(){
    color = f_color;
}