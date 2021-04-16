package demo_balls;

import components.GameObject;
import components.Sprite;
import components.Transform;
import components.shape.Circle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.system.CallbackI;

public class BallObject extends GameObject {
    public final int id;
    private Vector2f velocity = new Vector2f();
    private float radius = 25;
    private Sprite spr;
    private Vector4f color;

    public BallObject(int id, Transform transform, Vector2f velocity, Vector4f color){
        super("ball #" + id, transform);
        this.id = id;
        this.velocity.set(velocity);
        this.color = color;
    }


    @Override
    public void init(){
        spr = new Sprite(new Circle(radius, 25, transform));
        addComponent(spr);
        spr.color = color;
    }

    @Override
    public void update(float dt){

        // Collision with the walls
        Vector2f futruePos = new Vector2f(
                transform.position.x + velocity.x * dt,
                transform.position.y + velocity.y * dt
        );

        boolean collision_x = false, collision_y = false;

        if(futruePos.x + radius >= frameSize){
            velocity.x *= -1;
            collision_x = true;
            transform.position.x = frameSize - radius;
        }

        else if(futruePos.x - radius <= -frameSize){
            velocity.x *= -1;
            collision_x = true;
            transform.position.x = -frameSize + radius;
        }

        if(futruePos.y + radius >= frameSize){
            velocity.y *= -1;
            collision_y = true;
            transform.position.y = frameSize - radius;
        }

        else if(futruePos.y - radius <= -frameSize){
            velocity.y *= -1;
            collision_y = true;
            transform.position.y = -frameSize + radius;
        }

        if (!collision_x) transform.position.x = futruePos.x;
        if (!collision_y) transform.position.y = futruePos.y;

        checkForBallCollision();
    }

    public void checkForBallCollision(){
        for(BallObject ball : balls){
            if(ball.id == id) continue;
            float d = (float) Math.sqrt(
                    Math.pow(transform.position.x - ball.transform.position.x, 2) +
                            Math.pow(transform.position.y - ball.transform.position.y, 2));
            //if (d <= radius + ball.radius)
               // System.out.println("collision #" + id + " and #" + ball.id);
            // System.out.println("This: #" + id + "\nOther: #" + ball.id + "\nDistance: " + d);
        }
    }

    public static float frameSize;
    public static BallObject[] balls;
    public static BallObject[][][] balls_arr;

    public static float[] gridSort(int factor){
        float cellSize = ((frameSize * 2) / factor);
        float[] cells = new float[factor];
        for(int i = 0; i <= factor; i++)
            cells[i] = i * cellSize - frameSize;
        return cells;
    }
}
