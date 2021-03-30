package Test;

import Scenes.Scene;
import components.GameObject;
import components.Sprite;
import components.Transform;
import components.shapes.Rectangle;
import components.shapes.Shape;
import graphics.Shader;
import org.joml.Vector2f;

public class SecondTestScene extends Scene {

    public SecondTestScene(){
        super("second test scene");
    }

    public Shape shape;
    public GameObject go;

    public void init(){

        Shader.LayoutElement[] layout = new Shader.LayoutElement[]{
                new Shader.LayoutElement(Shader.LayoutElementType.POSITION, 2),
        };

        go = new GameObject("test",
                new Transform(
                        new Vector2f(50, 25),
                        new Vector2f(50, 100)
                ));

        go.addComponent(new Sprite(new Rectangle(), ));
    }

    @Override
    public void SceneUpdate(float dt) {

    }
}
