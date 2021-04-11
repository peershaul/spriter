package graphics;

import components.FrameManager;
import components.GameObject;
import components.DrawingElement;
import components.Sprite;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    private List<DrawingElement> sprites;
    private float[] vertexData;
    private int[] indexData;
    private ArrayBuffer arrayBuffer;
    private Shader shader;
    private boolean lines = false;
    private boolean ConstMode = false;
    private int drawMode = 0;

    public void addSprite(DrawingElement spr){ sprites.add(spr); }

    public void addSprite(GameObject go) {
        DrawingElement spr = go.getComponent(Sprite.class);
        if(spr == null) spr = go.getComponent(FrameManager.class);
        if(spr == null){
            System.out.println("DrawingElement wasnt found on GameObject: " + go.getName());
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

    public Renderer(Shader shader, int[] layout, boolean ConstMode){
        this.shader = shader;
        sprites = new ArrayList<>();

        arrayBuffer = new ArrayBuffer(
                new VertexBuffer(),
                new IndexBuffer(),
                layout
        );

        this.ConstMode = ConstMode;
    }

    public boolean getConstMode(){ return ConstMode; }

    public void refresh(){
        if(sprites.size() == 0) return;

        if(sprites.size() > 1)
            for(int j = 0; j < sprites.size(); j++) {
                int smallestIndex = Integer.MAX_VALUE;
                int smallestSlot = -1;

                for (int i = j; i < sprites.size(); i++) {
                    DrawingElement spr = sprites.get(i);
                    if (spr.zIndex < smallestIndex) {
                        smallestIndex = spr.zIndex;
                        smallestSlot = i;
                    }
                }

                DrawingElement slot = sprites.get(j);
                sprites.set(j, sprites.get(smallestSlot));
                sprites.set(smallestSlot, slot);
            }

        int vertexDataSize = 0;
        int indexDataSize = 0;

        for(DrawingElement spr : sprites) {
            vertexDataSize += spr.getVertexData().length;
            indexDataSize += spr.getIndexData().length;
        }

        vertexData = new float[vertexDataSize];
        int index = 0;

        for(DrawingElement spr : sprites){
            if(spr == null) continue;
            float[] spriteData = spr.getVertexData();
            for(int i = 0; i < spriteData.length; i++)
                vertexData[index + i] = spriteData[i];

            index += spriteData.length;
        }

        index = 0;
        indexData = new int[indexDataSize];
        int Max = -1;

        for(DrawingElement spr : sprites){
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

    }

    public Shader getShader() { return shader; }

    public void toggleWireframe(){
        lines = !lines;
        glPolygonMode(GL_FRONT_AND_BACK, lines? GL_LINE : GL_FILL);
    }


    public void setDrawMode(int mode){
        glPolygonMode(GL_FRONT_AND_BACK, mode);
        drawMode = mode;
    }


    public boolean draw(){
        refresh();

        if(sprites.size() == 0) return false;

        shader.bind();
        arrayBuffer.bind();

        assert drawMode == 0: "There is no drawMode defined in this renderer";

        glDrawElements(ConstMode? drawMode : ((lines)? GL_LINES : GL_TRIANGLES),
                arrayBuffer.getIndexBuffer().getAmount(), GL_UNSIGNED_INT, 0);

        int err = glGetError();
        while(err != GL_FALSE){
            System.out.println("[OPENGL ERROR] ( " + err + " )");
            err = glGetError();
        }

        arrayBuffer.unbind();
        shader.unbind();

        return true;
    }


    public String getDump(boolean textured){
        String res = "";
        int vertexSize = textured? 4 : 6;

        if(vertexData == null) return "empty vertex data";

        for(int i = 0; i < vertexData.length / vertexSize; i++) {
            for (int j = 0; j < vertexSize; j++)
                res += vertexData[i * vertexSize + j] + "\t";
            res += "\n";
        }

        res += "\n\n\n";

        for(int i = 0; i < indexData.length / 3; i++) {
            for (int j = 0; j < 3; j++)
                res += indexData[i * 3 + j] + "\t";
            res += "\n";
        }

        return res;
    }

}
