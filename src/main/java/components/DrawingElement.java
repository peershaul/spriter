package components;

public interface DrawingElement {
    int zIndex = 0;
    float[] getVertexData();
    int[] getIndexData();
}
