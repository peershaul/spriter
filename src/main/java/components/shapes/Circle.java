package components.shapes;

import components.ObjectRenderer;
import components.Transform;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Circle extends ObjectRenderer {

    public float radius;
    public final int resolution;
    public Vector3f color = new Vector3f(1, 1, 1);

    public Circle(float radius, int resolution){
        this.radius = radius;
        this.resolution = resolution;
        calc();
    }

    @Override
    public void calc(){
        this.vertexData = new float[(resolution + 1) * 2];
        Vector2f position = (gameObject == null)? new Vector2f() : gameObject.transform.position;


        float frac = (float) (2 * Math.PI) / resolution;
        int index = 2;

        vertexData[0] = position.x;
        vertexData[1] = position.y;

        for(int i = 0; i < resolution; i++){
            vertexData[index + 0] = position.x + radius * (float) Math.cos(i * frac);
            vertexData[index + 1] = position.y + radius * (float) Math.sin(i * frac);
            index += 2;
        }


        for(int i = 0; i < this.vertexData.length; i += 2){
            for(int j = 0; j < 2; j++)
                System.out.print(this.vertexData[i + j] + "\t");
            System.out.println();
        }

        this.indexData = new int[resolution * 3];

        index = 0;
       for(int i = 1; i < resolution; i++){
            this.indexData[index + 0] = 0;
            this.indexData[index + 1] = i;
            this.indexData[index + 2] = i - 1;

            index += 3;
       }
    }

}
