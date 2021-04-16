package Test;

import Scenes.Scene;
import graphics.Line;
import org.joml.Vector2f;
import utils.Window;

public class SecondTestScene extends Scene {

    public SecondTestScene(){
        super("second test scene");
    }

    private Line l;

    @Override
    public void init(){

        Vector2f screen = Window.getScreen();

        l = new Line(
                new Vector2f(0, 0),
                new Vector2f(40, screen.y * 0.95f / 2 )
        );
    }

    @Override
    public void update(float dt) {
        l.draw(Line.DrawScale.PIXELS);
    }

    @Override
    public void SceneUpdate(float dt) {

    }
}