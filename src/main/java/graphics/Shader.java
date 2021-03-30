package graphics;


import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.Scanner;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int id;
    private String[] srcs;
    private String filepath;
    private boolean bound = false;
    private boolean layoutSet = false;
    private LayoutElement[] layout;

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

    public void setLayout(LayoutElement[] layout) {
        if(layoutSet) System.out.println("shader layout already set '" + filepath + "'");
        else {this.layout = layout; layoutSet = true; }
    }

    public LayoutElement[] getLayout() { return layout; }

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
        bind();
        glUniform2f(location, vec.x, vec.y);
        unbind();
    }

    public void uploadVec3f(String name, Vector3f vec){
        int location = glGetUniformLocation(id, name);
        bind();
        glUniform3f(location, vec.x, vec.y, vec.z);
        unbind();
    }

    public void uploadTexture(String name, int slot){
        int location = glGetUniformLocation(id, name);
        bind();
        glUniform1i(location, slot);
        unbind();
    }

    public void uploadMat4f(String name, Matrix4f mat){
        int location = glGetUniformLocation(id, name);
        bind();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(4 * 4);
        mat.get(matBuffer);
        glUniformMatrix4fv(location, false, matBuffer);
        unbind();
    }


    public enum LayoutElementType { POSITION, COLOR, TEX_COORDS };

    public static class LayoutElement{
        public final int length;
        public final LayoutElementType type;

        public LayoutElement(LayoutElementType type, int length){
            this.length = length;
            this.type = type;
        }
    }
}
