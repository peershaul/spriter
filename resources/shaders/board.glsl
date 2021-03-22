#type vertex
#version 330 core

layout(location = 0) in vec2 v_pos;
layout(location = 1) in vec2 v_color;

uniform vec2 screen;

out vec4 f_color;

void main(){
    vec4 pos = vec4(v_pos.x / screen.x, v_pos.y / screen.y, 0, 1);
    gl_Position = pos;
    f_color = vec4(v_color.xy, 0, 1);
}

#type fragment
#version 330 core

in vec4 f_color;

out vec4 color;

void main(){
    color = f_color;
}