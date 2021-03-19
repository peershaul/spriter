package math;

public class Vector2f {
    public float x, y;

    public Vector2f set(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2f set(float m) { return set(m, m); }
    public Vector2f() { set(0, 0); }
    public Vector2f(float m) { set(m); }
    public Vector2f(float x, float y) { set(x, y); }

    public String toString(){ return "( " + x + ",\t" + y + " )"; }
}
