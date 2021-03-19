package graphics;

import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;

public class IndexBuffer extends Buffer<Integer>{
    @Override
    public void putData(Integer[] data, boolean dynamic) {
        this.data = data;
        int[] raw = new int[data.length];
        for(int i = 0; i < data.length; i++)
            raw[i] = data[i];

        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(raw).flip();

        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        unbind();
    }

    public void putData(int[] data){
        Integer[] cl = new Integer[data.length];
        for(int i = 0; i < data.length; i++)
            cl[i] = data[i];

        putData(cl, false);
    }

    public IndexBuffer(){
        super(GL_ELEMENT_ARRAY_BUFFER);
    }

    public int getAmount() { return data.length; }
}
