package graphics;

import math.Vector2f;
import math.Vector4f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int id;
    private String[] srcs;
    private String filepath;
    private boolean bound = false;

    public Shader(String filepath){
        this.filepath = filepath;
        srcs = new String[]{"", ""};
        try{
            Scanner reader = new Scanner(new File(filepath));
            int state = -1;
            // vertex shader = 0; fragment shader = 1
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                if(line.indexOf("#type") != -1)
                    state = (line.indexOf("vertex") == -1)? 1 : 0;

                else if (state != -1) srcs[state] += line + "\n";
            }

        } catch (FileNotFoundException e){
            System.out.println("Couldn't find file '" + filepath + "'");
            e.printStackTrace();
        }
    }

    public void Compile(){
        int[] shaders = compileShaders();

        id = glCreateProgram();
        for(int i = 0; i < shaders.length; i++)
            glAttachShader(id, shaders[i]);

        glLinkProgram(id);

        int success = glGetProgrami(id, GL_LINK_STATUS);
        if(success == GL_FALSE){
            int len = glGetProgrami(id, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filepath + "' Linking error");
            System.out.println(glGetProgramInfoLog(id, len));
            assert false : "";
        }
    }

    private int[] compileShaders(){
        int[] shaders = new int[2];
        int[] types = new int[]{GL_VERTEX_SHADER, GL_FRAGMENT_SHADER};

        for(int i = 0; i < shaders.length; i++){
            shaders[i] = glCreateShader(types[i]);
            glShaderSource(shaders[i], srcs[i]);
            glCompileShader(shaders[i]);

            int success = glGetShaderi(shaders[i], GL_COMPILE_STATUS);
            if(success == GL_FALSE){
                int len = glGetShaderi(shaders[i], GL_INFO_LOG_LENGTH);
                System.out.println("ERROR: '" + filepath + "' " + (i == 0? "Vertex" : "Fragment") + "shader compilation error");
                System.out.println(glGetShaderi(shaders[i], len));
                assert false : "";
            }
        }

        return shaders;
    }

    public void bind(){
        if(!bound){
            glUseProgram(id);
            bound = true;
        }
    }

    public void unbind(){
        if(bound){
            glUseProgram(0);
            bound = false;
        }
    }

    public void uploadVec2f(String varName, Vector2f vec){
        int location = glGetUniformLocation(id, varName);
        boolean wasBound = bound;
        if (!bound) bind();
        glUniform2f(location, vec.x, vec.y);
        if (!wasBound) unbind();
    }
}
