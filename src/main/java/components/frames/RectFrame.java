package components.frames;

import components.Transform;
import org.joml.Vector2f;

public class RectFrame extends Frame{

    public RectFrame(Transform transform){
        super(transform);
        vertexCount = 4;
    }

    public RectFrame(){
        super();
        vertexCount = 4;
    }

    @Override
    public Vector2f[] getPositions() {
        this.positions = new Vector2f[4];

        Vector2f[] signs = new Vector2f[]{
                new Vector2f(1, -1), // bottom right
                new Vector2f(-1, 1), // top left
                new Vector2f(1, 1), // top right
                new Vector2f(-1, -1) // bottom left
        };

        for(int i = 0; i < positions.length; i++)
            positions[i] = new Vector2f(
                    transform.position.x + (signs[i].x * transform.scale.x / 2),
                    transform.position.y + (signs[i].y * transform.scale.y / 2)
            );

        return positions;
    }

    @Override
    public int[] getIndices() {
        this.indices = new int[]{
                1, 2, 0, 3
        };
        return indices;
    }
}
