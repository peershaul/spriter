package components.shapes;

import components.Transform;
import graphics.Shader;
import org.joml.Vector2f;

public abstract class Shape {

    protected Vector2f[] vertices, texCoords;
    protected int[] indices;
    public Transform transform;

    public abstract void calc();

    public Vector2f[] getVertices(){
        calc();
        return vertices;
    }

    public int[] getIndices(){
        calc();
        return indices;
    }

    public Vector2f[] getTexCoords(){
        calc();
        return texCoords;
    }

}
