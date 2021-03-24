package Test;

import components.GameObject;
import components.ObjectRenderer;

public class TestGameObject extends GameObject {
    public TestGameObject() {
        super("Test game object");
    }

    @Override
    public void init(){
        ObjectRenderer obr = new ObjectRenderer();
        obr.vertexData = new float[]{
                // Position        Color
                200, 100,          1, 0, 0,
                100, 200,          0, 1, 0,
                200, 200,          1, 1, 0,
                100, 100,          0, 0, 1
        };
        obr.indexData = new int[]{
                2, 1, 0,
                0, 1, 3
        };

        addComponent(obr);
    }
}
