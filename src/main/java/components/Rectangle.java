package components;

import math.Vector2f;
import math.Vector3f;

public class Rectangle extends ObjectRenderer{

    public Vector3f color;

    public void changeColor(Vector3f color){
        this.color = color;
        calc();
    }

    public Rectangle(){
        color = new Vector3f(1);
    }

    @Override
    public void calc(){
        if (gameObject == null) return;


        Transform transform = gameObject.transform;

        Vector2f[] signs = new Vector2f[]{
                new Vector2f( 1, -1),
                new Vector2f(-1,  1),
                new Vector2f( 1,  1),
                new Vector2f(-1, -1)
        };

        this.vertexData = new float[4 * (3 + 2)];
        int index = 0;
        for(int i = 0; i < 4; i++){
            this.vertexData[index + 0] = transform.position.x + (signs[i].x * transform.scale.x / 2);
            this.vertexData[index + 1] = transform.position.y + (signs[i].y * transform.scale.y / 2);
            this.vertexData[index + 2] = color.x;
            this.vertexData[index + 3] = color.y;
            this.vertexData[index + 4] = color.z;
            index += 3 + 2;
        }

        this.indexData = new int[]{
                2, 1, 0,
                0, 1, 3
        };
    }
}
