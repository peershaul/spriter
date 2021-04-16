#type vertex
#version 330 core

layout (location = 0) in vec2 position;
layout (location = 1) in vec4 a_color;

uniform vec2 screen;

out vec4 f_color;

float calcCamCoords(bool axis){
    float pos = axis? position.x : position.y;
    float cam = axis? screen.x : screen.y;
    return 2 * pos / cam;
}

void main(){
    f_color = a_color;
    gl_Position = vec4(calcCamCoords(true), calcCamCoords(false), 0, 1);
}

#type fragment
#version 330 core

in vec4 f_color;

out vec4 color;

void main(){
    color = f_color;
}