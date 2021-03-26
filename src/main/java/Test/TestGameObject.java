package Test;

import components.GameObject;
import components.Rectangle;
import components.Transform;
import math.Vector2f;
import math.Vector3f;

public class TestGameObject extends GameObject {
    public TestGameObject() {
        super("Test game object");
        this.transform = new Transform(
                new Vector2f(100, 200),
                new Vector2f(300, 200)
        );
    }

    private float time = 0.0f;
    private int dir = -1;
    private Rectangle rect;

    @Override
    public void init(){
        rect = new Rectangle();
        // rect.color = new Vector3f(0, 1, 0);
        rect.calc();

        addComponent(rect);
    }

    @Override
    public void update(float dt){
        time += dt;

        if(time >= 2){
            dir *= -1;
            time = 0;
        }

        transform.position  = transform.position.add(new Vector2f(20, 50).mul(dt * dir));
        transform.scale = transform.scale.add(new Vector2f(0, 50).mul(dt * dir));
        rect.changeColor(rect.color.add(new Vector3f(0.5f, 0f, 0f).mul(dt * dir)));
    }
}
