package components.shapes;

import org.joml.Vector2f;

public class Rectangle extends Shape{
    @Override
    public void calc() {
        if (this.transform == null) return;

        Vector2f[] signs = new Vector2f[]{
                new Vector2f( 1, -1),
                new Vector2f(-1,  1),
                new Vector2f( 1,  1),
                new Vector2f(-1, -1)
        };

        this.texCoords = new Vector2f[]{
                new Vector2f(1, 0),
                new Vector2f(0, 1),
                new Vector2f(1, 1),
                new Vector2f(0, 0)
        };

        this.vertices = new Vector2f[4];
        for(int i = 0; i < vertices.length; i++)
            vertices[i] = new Vector2f(
                    transform.position.x + (signs[i].x * transform.scale.x / 2),
                    transform.position.y + (signs[i].y * transform.scale.y / 2)
            );

        this.indices = new int[]{
                2, 1, 0,
                0, 1, 3
        };
    }
}