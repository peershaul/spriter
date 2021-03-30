package graphics;


import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public abstract class Buffer<T> {
    protected int id, type;
    protected T[] data;

    public abstract void putData(T[] data, boolean dynamic);

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
