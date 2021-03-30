package components;

import org.joml.Vector2f;

public class Transform {
    public Vector2f position, scale;

    public Transform(Vector2f position, Vector2f scale){
        this.position = position;
        this.scale = scale;
    }

    public Transform(Vector2f position){
        this.position = position;
        this.scale = new Vector2f();
    }

    public Transform(){
        this.position = new Vector2f();
        this.scale = new Vector2f();
    }
}
