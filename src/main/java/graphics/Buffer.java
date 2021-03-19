package graphics;


import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public abstract class Buffer<T> {
    protected int id, type;
    protected T[] data;
    protected boolean bound = false;

    public abstract void putData(T[] data, boolean dynamic);

    protected Buffer(int type){
        this.type = type;

        id = glGenBuffers();
    }

    public void bind(){
        if(!bound){
            glBindBuffer(type, id);
            bound = true;
        }
    }

    public void unbind(){
        if(bound){
            glBindBuffer(type, 0);
            bound = false;
        }
    }
}
