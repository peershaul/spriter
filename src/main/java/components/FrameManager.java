package components;

import components.frames.Frame;
import org.joml.Vector2f;
import org.joml.Vector4f;
import utils.Color;

public class FrameManager extends Component implements DrawingElement{

    protected float[] vertexData;
    protected int[] indexData;
    public int zIndex = 0;
    public Frame frame;
    public Vector4f color;

    public FrameManager(Frame frame){
        this.frame = frame;
        color = Color.WHITE;
    }

    public FrameManager(){
        this.frame = null;
        color = Color.WHITE;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public float[] getVertexData() {
        if(frame == null) return new float[0];

        int elemSize = 6;
        this.vertexData = new float[frame.getVertexCount() * elemSize];

        Vector2f[] positions = frame.getPositions();

        int index = 0;
        for(int i = 0; i < frame.getVertexCount(); i++){
            vertexData[index] = positions[i].x;
            vertexData[index + 1] = positions[i].y;

            vertexData[index + 2] = color.x;
            vertexData[index + 3] = color.y;
            vertexData[index + 4] = color.z;
            vertexData[index + 5] = color.w;

            index += elemSize;
        }

        return vertexData;
    }

    @Override
    public int[] getIndexData() {
        if(frame == null) return new int[0];

        indexData = frame.getIndices();
        return indexData;
    }

    public void changeFrame(Frame frame){
        this.frame = frame;
        frame.setTransform(gameObject.transform);
    }

    @Override
    public int getZIndex(){ return zIndex; }
}
