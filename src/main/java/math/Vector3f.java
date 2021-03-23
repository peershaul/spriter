package math;

public class Vector3f {
    public float x, y, z;

    public Vector3f set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    public Vector3f set(float x, float y) { return set(x, y, 0); }
    public Vector3f set(float m) { return set(m, m); }

    public Vector3f(float x, float y, float z){ set(x, y, z); }
    public Vector3f(float x, float y) { set(x, y); }
    public Vector3f(float m) { set(m); }
    public Vector3f() { set(0); }

    public Vector3f normalize(){ return new Vector3f(this.x / 255, this.y / 255, this.z / 255); }
}
