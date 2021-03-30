package graphics;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class ArrayBuffer {
    private int id;
    private IndexBuffer ib;
    private VertexBuffer vb;
    private int[] layout;

    public void bindLayout(){
        for(int i = 0; i < layout.length; i++)
            glEnableVertexAttribArray(i);
    }

    public void unbindLayout(){
        for(int i = 0; i < layout.length; i++)
            glDisableVertexAttribArray(i);
    }

    public VertexBuffer getVertexBuffer() { return vb; }
    public IndexBuffer getIndexBuffer() { return ib; }

    public void bind(){
        glBindVertexArray(id);
        vb.bind();
        ib.bind();
        bindLayout();
    }

    public void unbind(){
        vb.unbind();
        ib.bind();
        unbindLayout();
        glBindVertexArray(0);
    }

    public ArrayBuffer(VertexBuffer vb, IndexBuffer ib, int[] layout){
        this.vb = vb;
        this.ib = ib;
        this.layout = layout;

        this.id = glGenVertexArrays();

        bind();
        int totalSize = 0;
        for(int i = 0; i < layout.length; i++)
            totalSize += layout[i];

        int stride = 0;
        for(int i = 0; i < layout.length; i++){
            glVertexAttribPointer(i, layout[i], GL_FLOAT, false, totalSize * Float.BYTES, stride * Float.BYTES);
            stride += layout[i];
        }
    }

}

