package math;

public class Vector4f {
    public float x, y, z, w;

    public Vector4f set(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vector4f set(float x, float y, float z){ return set(x, y, z, 1.0f); }
    public Vector4f set(float x, float y) { return set(x, y, 0.0f, 1.0f); }
    public Vector4f set(float m) { return set(m, m); }

    public Vector4f() { set(0.0f); }
    public Vector4f(float m) { set(m); }
    public Vector4f(float x, float y) { set(x, y); }
    public Vector4f(float x, float y, float z) { set(x, y, z); }
    public Vector4f(float x, float y, float z, float w) { set(x, y, z, w); }

    public String toString(){ return "( " + x + ",\t" + y + ",\t" + z + ",\t" + w + " )"; }
}
