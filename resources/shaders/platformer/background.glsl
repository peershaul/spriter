#type vertex
#version 330 core

layout (location = 0) in vec2 v_pos;
layout (location = 1) in vec4 v_color;

out vec4 f_color;

void main(){
    gl_Position = vec4(2 * v_pos, 0, 1);
    f_color = v_color;
}

#type fragment
#version 330 core

in vec4 f_color;

out vec4 color;

void main(){
    color = f_color;
}