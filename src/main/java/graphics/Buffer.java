package graphics;


import static org.lwjgl.opengl.GL15.*;

public abstract class Buffer<T> {
    protected int id, type;
    protected T[] data;
    protected boolean started = false;

    public abstract void putData(T[] data, boolean dynamic);

    public abstract void updateData(T[] data);

    protected Buffer(int type){
        this.type = type;

        id = glGenBuffers();
    }

    public void bind(){
        glBindBuffer(type, id);
    }

    public void unbind(){
        glBindBuffer(type, 0);
    }
}
