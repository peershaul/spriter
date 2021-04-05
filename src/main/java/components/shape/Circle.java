package components.shape;

import components.Transform;
import org.joml.Vector2f;

public class Circle extends Shape{

    public Circle(float radius, int resolution){
        this.radius = radius;
        this.resolution = resolution;
        this.transform = new Transform();
        vertexCount = resolution + 1;
    }

    public Circle(float radius, int resolution, Transform transform){
        this.radius = radius;
        this.resolution = resolution;
        this.transform = transform;
        vertexCount = resolution + 1;
    }

    public float radius;
    public int resolution;

    @Override
    public Vector2f[] getPositions() {
        this.positions = new Vector2f[resolution + 1];
        double diff = 2 * Math.PI / resolution;

        positions[0] = new Vector2f(transform.position);

        for(int i = 0; i < resolution; i++){
            positions[i + 1] = new Vector2f(
                    transform.position.x + (float) (radius * Math.sin(i * diff)),
                    transform.position.y + (float) (radius * Math.cos(i * diff))
            );
        }

        return positions;
    }

    @Override
    public Vector2f[] getTexCoords() {
        return new Vector2f[0];
    }

    @Override
    public int[] getIndices() {
        this.indices = new int[resolution * 3];

        for(int i = 0; i < resolution - 1; i++) {
            indices[i * 3] = 0;
            indices[i * 3 + 1] = i + 2;
            indices[i * 3 + 2] = i + 1;
        }

        indices[indices.length - 3] = 0;
        indices[indices.length - 2] = 1;
        indices[indices.length - 1] = resolution;

        return indices;
    }
}
