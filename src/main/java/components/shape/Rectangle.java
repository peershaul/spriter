package components.shape;

import components.Transform;
import org.joml.Vector2f;

public class Rectangle extends Shape{


    public Rectangle(Transform transform) {
        super(transform);
        vertexCount = 4;
    }


    public Rectangle(){
        super();
        vertexCount = 4;
    }



    @Override
    public Vector2f[] getPositions(){
        this.positions = new Vector2f[4];

        Vector2f[] signs = new Vector2f[]{
            new Vector2f(1, -1),
            new Vector2f(-1, 1),
            new Vector2f(1, 1),
            new Vector2f(-1, -1)
        };

        for(int i = 0; i < positions.length; i++)
            positions[i] = new Vector2f(
                    transform.position.x + (signs[i].x * transform.scale.x / 2),
                    transform.position.y + (signs[i].y * transform.scale.y / 2)
            );

        return positions;
    }

    @Override
    public Vector2f[] getTexCoords() {
        this.texCoords = new Vector2f[]{
                new Vector2f(1, 1),
                new Vector2f(0, 0),
                new Vector2f(1, 0),
                new Vector2f(0, 1)
        };
        return texCoords;
    }

    @Override
    public int[] getIndices() {
        this.indices = new int[]{
                2, 1, 0,
                0, 1, 3
        };
        return indices;
    }
}
