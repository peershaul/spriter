package example_platformer;

import Scenes.Scene;
import components.GameObject;
import components.shapes.Rectangle;
import components.Transform;

import org.joml.Vector2f;
import org.joml.Vector3f;
import utils.KeyListener;
import utils.Window;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static utils.Color.normalize;

public class PlatformerPlayer extends GameObject {
    public PlatformerPlayer() {
        super("Player");
    }

    private boolean jumping = false;
    private Vector2f velocity = new Vector2f(50, 0);
    private final Vector2f acceleration = new Vector2f(0, -1333);
    public Scene parent;

    private float groundHeight;
    private final float jumpingVelocity = 666;
    private float time = 0;

    @Override
    public void init(){
        Rectangle rect = new Rectangle();
        Vector2f screen = Window.getScreen();

        groundHeight = 0.1f * screen.y + 50;

        transform = new Transform(
                new Vector2f(100, groundHeight),
                new Vector2f(100)
        );

        rect.changeColor(normalize(new Vector3f(204, 0, 0)));
        rect.calc();

        addComponent(rect);
    }

    @Override
    public void update(float dt){

        transform.position.x += velocity.x * dt;

        if (transform.position.y + velocity.y * dt <= groundHeight && jumping){
            transform.position.y = groundHeight;
            velocity.y = 0;
            jumping = false;
            System.out.println(time);
            time = 0;
        }
        else transform.position.y += velocity.y * dt;

        if(jumping){
            velocity.y += acceleration.y * dt;
            time += dt;
        }

        if(!jumping && KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
            jumping = true;
            velocity.y = jumpingVelocity;
        }

    }


}
