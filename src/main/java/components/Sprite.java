package components;

import components.shapes.Shape;
import graphics.Shader;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.lang.annotation.ElementType;
import java.lang.module.ModuleDescriptor;


public class Sprite extends Component{
    private float[] vertexData;
    private int[] indexData;
    public Shape shape;
    public final Shader.LayoutElement[] layout;

    public Vector4f color;

    public Sprite(Shape shape, Shader.LayoutElement[] layout){
        this.shape = shape;
        this.layout = layout;
        shape.transform = gameObject.transform;
    }


    public void calc(){
        if(shape == null) return;

        indexData = shape.getIndices();

        Vector2f[] positions = shape.getVertices();
        Vector2f[] texCoords = shape.getTexCoords();
        int layoutTotalAmount = 0;
        for(Shader.LayoutElement elem : layout)
            layoutTotalAmount += elem.length;

        vertexData = new float[layoutTotalAmount * positions.length];
        int offset = 0;
        for(int i = 0; i < positions.length; i++)
            for(Shader.LayoutElement elem : layout){
                switch (elem.type) {
                    case POSITION -> {
                        vertexData[offset] = positions[i].x;
                        vertexData[offset + 1] = positions[i].y;
                    }
                    case COLOR -> {
                        vertexData[offset] = color.x;
                        vertexData[offset + 1] = color.y;
                        vertexData[offset + 2] = color.z;
                        vertexData[offset + 3] = color.w;
                    }
                    case TEX_COORDS -> {
                        vertexData[offset] = texCoords[i].x;
                        vertexData[offset + 1] = texCoords[i].y;
                    }
                    default -> System.out.println("Undefined layout type");
                }
                offset += elem.length;
            }

    }


    @Override
    public void update(float dt) {
        calc();
    }
}
