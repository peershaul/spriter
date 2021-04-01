package components.shape;

import components.Transform;
import org.joml.Vector2f;

public abstract class Shape {
    public Vector2f[] positions, texCoords;
    protected int[] indices;
    protected Transform transform;
    public int vertexCount;

    public Shape(Transform transform){
        this.transform = transform;
    }

    public Shape(){

    }

    public void setTransform(Transform transform){
        this.transform = transform;
    }

    public abstract Vector2f[] getPositions();
    public abstract Vector2f[] getTexCoords();
    public abstract int[] getIndices();

    public int getVertexCount(){
        return vertexCount;
    }
}
