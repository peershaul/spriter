package platformer_demo;

import components.GameObject;
import components.Sprite;
import components.Transform;
import components.shape.Rectangle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import utils.KeyListener;
import utils.Window;

import java.security.Key;
import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;
import static utils.Color.normalize;

public class PlatformerPlayer extends GameObject {
    public PlatformerPlayer(){
        super("platformer player");
        screen = Window.getScreen();
        groundloc = (0.1f * screen.y) + 37.5f;
        this.transform = new Transform(
                new Vector2f(75, groundloc),
                new Vector2f(75)
        );
    }

    private Vector2f screen;
    private final float groundloc;

    private boolean jumping = false;
    private Vector2f velocity = new Vector2f(0);
    private final Vector2f acceleration = new Vector2f(0, -1333);

    private final float jmpVel = 666;
    private final float walkVel = 200;

    @Override
    public void start(){
        Sprite spr = new Sprite();
        spr.textured = true;
        addComponent(spr);

        spr.changeShape(new Rectangle());
        spr.color = normalize(new Vector4f(255, 80, 80, 1));
    }

    @Override
    public void ObjectUpdate(float dt){
        if(transform.position.y + velocity.y * dt <= groundloc && jumping){
            transform.position.y = groundloc;
            velocity.y = 0;
            jumping = false;
        }
        else transform.position.y += velocity.y * dt;

        if(jumping)
            velocity.y += acceleration.y * dt;

        if(!jumping && KeyListener.isKeyPressed(GLFW_KEY_SPACE)){
            jumping = true;
            velocity.y = jmpVel;
        }

        transform.position.x += velocity.x * dt;

        boolean key_a = KeyListener.isKeyPressed(GLFW_KEY_A);
        boolean key_d = KeyListener.isKeyPressed(GLFW_KEY_D);

        if(!jumping) {
            if (key_a && !key_d)
                velocity.x = -walkVel;
            else if (!key_a && key_d)
                velocity.x = walkVel;
            else
                velocity.x = 0;
        }
    }


}
