package Test;

import components.GameObject;
import components.ObjectRenderer;

public class TestGameObject extends GameObject{

    public TestGameObject(){
        super("Test game object");
    }

    @Override
    public void init(){
        addComponent(new ObjectRenderer());
    }

}