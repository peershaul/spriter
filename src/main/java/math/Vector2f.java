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

    @Override
    public Vector2f clone() { return new Vector2f(this.x, this.y); }

    public Vector2f sub(Vector2f vec){ return new Vector2f(this.x - vec.x, this.y - vec.y); }
    public Vector2f add(Vector2f vec){ return new Vector2f(this.x + vec.x, this.y + vec.y); }
    public Vector2f mul(float mul){ return new Vector2f(this.x * mul, this.y * mul);}
    public Vector2f mul(Vector2f vec) { return new Vector2f(this.x * vec.x, this.y * vec.y); }
}
