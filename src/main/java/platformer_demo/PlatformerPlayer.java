package platformer_demo;

import components.GameObject;
import components.Sprite;
import components.Transform;
import components.shape.Rectangle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import utils.Window;

import static utils.Color.normalize;

public class PlatformerPlayer extends GameObject {
    public PlatformerPlayer(){
        super("platformer player");
        screen = Window.getScreen();
        this.transform = new Transform(
                new Vector2f(75, (0.1f * screen.y) + 37.5f),
                new Vector2f(75)
        );

        System.out.println(this.transform.position + "\n" + this.transform.scale);
    }

    private Vector2f screen;

    @Override
    public void start(){
        Sprite spr = new Sprite();
        spr.textured = true;
        addComponent(spr);

        spr.changeShape(new Rectangle());
        spr.color = normalize(new Vector4f(255, 80, 80, 1));
    }



}
