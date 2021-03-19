package graphics;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer extends Buffer<Float>{
    @Override
    public void putData(Float[] data, boolean dynamic) {
        this.data = data;

        float[] raw = new float[data.length];
        for(int i = 0; i < data.length; i++)
            raw[i] = data[i];

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(raw).flip();
        bind();
        glBufferData(type, buffer, (dynamic? GL_DYNAMIC_DRAW : GL_STATIC_DRAW));
        unbind();
    }

    public void putData(float[] data, boolean dynamic){
        Float[] cli = new Float[data.length];
        for(int i = 0; i < cli.length; i++)
            cli[i] = data[i];

        putData(cli, dynamic);
    }

    public VertexBuffer(){
        super(GL_ARRAY_BUFFER);
    }
}
