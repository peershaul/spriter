package components;


import components.shape.Shape;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Sprite extends Component{

    protected float[] vertexData;
    protected int[] indexData;
    public Shape shape;
    public boolean textured = false;
    public Vector4f color;

    public Sprite(Shape shape){
        this.shape = shape;
        color = new Vector4f(1, 1,1,1);
    }

    public Sprite(){
        this.shape = null;
        color = new Vector4f(1, 1, 1, 1);
    }

    public float[] getVertexData(){

        if (shape == null) return new float[0];

        int elemSize = (textured)? 4 : 6;
        this.vertexData = new float[shape.getVertexCount() * elemSize];

        Vector2f[] positions = shape.getPositions();
        Vector2f[] texCoords = shape.getTexCoords();

        int index = 0;
        for(int i = 0; i < shape.getVertexCount(); i++){
            vertexData[index] = positions[i].x;
            vertexData[index + 1] = positions[i].y;

            if(textured){
                vertexData[index + 2] = texCoords[i].x;
                vertexData[index + 3] = texCoords[i].y;
            }

            else {
                vertexData[index + 2] = color.x;
                vertexData[index + 3] = color.y;
                vertexData[index + 4] = color.z;
                vertexData[index + 5] = color.w;
            }

            index += elemSize;
        }

        return vertexData;
    }

    public int[] getIndexData(){

        if(shape == null) return new int[0];

        indexData = shape.getIndices();
        return indexData;
    }


    public void changeShape(Shape shape){
        this.shape = shape;
        shape.setTransform(gameObject.transform);
    }


    @Override
    public void update(float dt) {

    }
}
