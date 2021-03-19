package Scenes;

import graphics.ArrayBuffer;
import graphics.IndexBuffer;
import graphics.Shader;
import graphics.VertexBuffer;
import math.Vector2f;
import math.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class TestScene extends Scene{

    public TestScene(String name){
        super(name);
    }

    private float[] vertices = {
            // Position       Color
             0.5f, -0.5f,     1.0f, 1.0f, 0.0f, 1.0f,
            -0.5f,  0.5f,     1.0f, 0.0f, 0.0f, 1.0f,
             0.5f,  0.5f,     0.0f, 1.0f, 1.0f, 1.0f,
            -0.5f, -0.5f,     1.0f, 0.0f, 1.0f, 1.0f
    };

    private int[] indices = {
            2, 1, 0,
            0, 1, 3
    };

    private ArrayBuffer arrayBuffer;
    private Shader shader;

    @Override
    public void init(){
        VertexBuffer vertexBuffer = new VertexBuffer();
        vertexBuffer.putData(vertices, true);

        IndexBuffer indexBuffer = new IndexBuffer();
        indexBuffer.putData(indices);

        int[] layout = {2, 4};

        arrayBuffer = new ArrayBuffer(vertexBuffer, indexBuffer, layout);

        shader = new Shader("resources/shaders/basic.glsl");
        shader.Compile();

        Vector2f vec2 = new Vector2f(40, 78);
        System.out.println(vec2);
        Vector4f vec4 = new Vector4f(40, 23, 60);
        System.out.println(vec4);
    }

    @Override
    public void update(float dt) {
        shader.bind();
        arrayBuffer.bind();

        glDrawElements(GL_TRIANGLES, arrayBuffer.getIndexBuffer().getAmount(), GL_UNSIGNED_INT, NULL);

        arrayBuffer.unbind();
        shader.unbind();
    }
}
