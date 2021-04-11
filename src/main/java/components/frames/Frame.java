package components.frames;

import components.Transform;
import org.joml.Vector2f;

public abstract class Frame {
    protected Vector2f[] positions;
    protected int[] indices;
    protected Transform transform;
    public int vertexCount = 0;

    public Frame(Transform transform){
        this.transform = transform;
    }

    public Frame(){

    }

    public void setTransform(Transform transform){
        this.transform = transform;
    }

    public abstract Vector2f[] getPositions();
    public abstract int[] getIndices();

    public int getVertexCount() { return vertexCount; }
}
