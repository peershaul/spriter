package graphics;

import components.GameObject;
import components.Sprite;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    private List<Sprite> sprites;
    private float[] vertexData;
    private int[] indexData;
    private ArrayBuffer arrayBuffer;
    private Shader shader;
    private boolean lines = false;
    private boolean printed = false;

    public void addSprite(Sprite spr){ sprites.add(spr); }

    public void addSprite(GameObject go) {
        Sprite spr = go.getComponent(Sprite.class);
        if(spr == null){
            System.out.println("Sprite wasnt found on GameObject: " + go.getName());
            return;
        }

        sprites.add(spr);
    }

    public Renderer(Shader shader, int[] layout){
        this.shader = shader;
        sprites = new ArrayList<>();

        arrayBuffer = new ArrayBuffer(
                new VertexBuffer(),
                new IndexBuffer(),
                layout
        );
    }

    public void refresh(){
        if(sprites.size() == 0) return;
        int vertexDataSize = 0;
        int indexDataSize = 0;

        for(Sprite spr : sprites) {
            vertexDataSize += spr.getVertexData().length;
            indexDataSize += spr.getIndexData().length;
        }

        vertexData = new float[vertexDataSize];
        int index = 0;

        for(Sprite spr : sprites){
            if(spr == null) continue;
            float[] spriteData = spr.getVertexData();
            for(int i = 0; i < spriteData.length; i++)
                vertexData[index + i] = spriteData[i];

            index += spriteData.length;
        }

        index = 0;
        indexData = new int[indexDataSize];
        int Max = -1;

        for(Sprite spr : sprites){
            if(spr == null) continue;
            int[] indices = spr.getIndexData();
            int max = 0;

            for(int i = 0; i < indices.length; i++){
                int value = indices[i] + Max + 1;
                indexData[i + index] = value;
                if(value > max) max = value;
            }

            Max = max;
            index += indices.length;
        }

        arrayBuffer.getVertexBuffer().putData(vertexData, true);
        arrayBuffer.getIndexBuffer().putData(indexData);

        if(!printed) {

            for (int i = 0; i < vertexData.length / 6; i++) {
                for (int j = 0; j < 6; j++)
                    System.out.print(vertexData[i * 6 + j] + "\t");
                System.out.println();
            }

            for(int i = 0; i < indexData.length / 3; i++){
                for(int j = 0; j < 3; j++)
                    System.out.print(indexData[i * 3 + j] + "\t");
                System.out.println();
            }
            printed = true;
        }
    }

    public Shader getShader() { return shader; }

    public void toggleWireframe(){
        lines = !lines;
        glPolygonMode(GL_FRONT_AND_BACK, lines? GL_LINE : GL_FILL);
    }


    public void draw(){
        refresh();

        shader.bind();
        arrayBuffer.bind();

        glDrawElements((lines)? GL_LINES : GL_TRIANGLES,
                arrayBuffer.getIndexBuffer().getAmount(), GL_UNSIGNED_INT, 0);

        int err = glGetError();
        while(err != GL_FALSE){
            System.out.println("[OPENGL ERROR] ( " + err + " )");
            err = glGetError();
        }

        arrayBuffer.unbind();
        shader.unbind();
    }

}
