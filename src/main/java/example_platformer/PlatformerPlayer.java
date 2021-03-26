package example_platformer;

import components.GameObject;
import components.Rectangle;
import components.Transform;
import math.Vector2f;
import math.Vector3f;
import org.lwjgl.system.CallbackI;
import utils.KeyListener;
import utils.Window;

import java.security.Key;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class PlatformerPlayer extends GameObject {
    public PlatformerPlayer() {
        super("Player");
    }

    private boolean jumping = false;
    private Vector2f velocity = new Vector2f(50, 0),
                        acceleration = new Vector2f(0, -30);

    @Override
    public void init(){
        Rectangle rect = new Rectangle();
        Vector2f screen = Window.getScreen();
        transform = new Transform(
                new Vector2f(100, 0.1f * screen.y + 50),
                new Vector2f(100)
        );

        rect.changeColor(new Vector3f(204, 0, 0).normalize());
        rect.calc();

        addComponent(rect);
    }

    @Override
    public void update(float dt){

        Vector2f screen = Window.getScreen();

        transform.position.x += velocity.x * dt;

        if (transform.position.y + velocity.y * dt <= 0.1f * screen.y + 50){
            transform.position.y = 0.1f * screen.y + 50;
            velocity.y = 0;
            jumping = false;
        }
        else transform.position.y += velocity.y * dt;

        if(jumping) velocity.y += acceleration.y * dt;

        if(!jumping && KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
            jumping = true;
            velocity.y = 75;
        }

    }


}
