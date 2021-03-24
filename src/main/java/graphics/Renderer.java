package graphics;

import components.ObjectRenderer;

import java.util.*;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {
    private ArrayBuffer arrayBuffer;
    private final Shader shader;
    private List<ObjectRenderer> objectRenderers = new ArrayList<>();
    private int[] shaderLayout = new int[0];

    public Renderer(Shader shader, int[] layout){
        this.shader = shader;
        if(layout.length > 0)
            this.shaderLayout = layout;
        else
            System.out.println("shader layout is empty");
    }


    public void addObjectRenderer(ObjectRenderer object){
        for(ObjectRenderer ob : objectRenderers) if(object.equals(ob)) return;
        objectRenderers.add(object);
        updateBuffer();
    }

    public void removeObjectRenderer(ObjectRenderer object){
        objectRenderers.remove(object);
        updateBuffer();
    }

    private void updateBuffer(){
        int dataCount = 0, indexCount = 0;
        for(ObjectRenderer obr : objectRenderers){
            dataCount += obr.vertexData.length;
            indexCount += obr.indexData.length;
        }

        float[] vertexData = new float[dataCount];
        int[] indices = new int[indexCount];

        int index = 0;
        for(ObjectRenderer obr : objectRenderers) {
            for (int i = 0; i < obr.vertexData.length; i++)
                vertexData[index + i] = obr.vertexData[i];
            index += obr.vertexData.length;
        }

        index = 0;
        int maxOb = -1;
        for(ObjectRenderer obr : objectRenderers){
            int max = 0;
            for(int i = 0; i < obr.indexData.length; i++) {
                indices[index + i] = obr.indexData[i] + maxOb + 1;
                if(indices[index + i] >= max) max = indices[index + i];
            }
            index += obr.indexData.length;
            maxOb = max;
        }

        VertexBuffer vb = new VertexBuffer();
        vb.putData(vertexData, true);

        IndexBuffer ib = new IndexBuffer();
        ib.putData(indices);

        if(shaderLayout.length == 0) System.out.println("shader layout not defined!");
        arrayBuffer = new ArrayBuffer(vb, ib, shaderLayout);
    }


    public Shader getShader() { return shader; }

    public void draw(){
        shader.bind();
        arrayBuffer.bind();

        glDrawElements(GL_TRIANGLES, arrayBuffer.getIndexBuffer().getAmount(), GL_UNSIGNED_INT, NULL);

        int err = glGetError();
        while(err != GL_FALSE){
            System.out.println("OPENGL ERROR ( " + err + " )");
            err = glGetError();
        }

        arrayBuffer.unbind();
        shader.unbind();
    }


}
