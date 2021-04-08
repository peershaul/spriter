package utils;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Color {

    public static Vector3f normalize(Vector3f ogColor) { return new Vector3f(ogColor.x, ogColor.y, ogColor.z).div(255); }
    public static Vector4f normalize(Vector4f ogColor){
        Vector3f res = normalize(new Vector3f(ogColor.x, ogColor.y, ogColor.z));
        return new Vector4f(res.x, res.y, res.z, ogColor.w);
    }

    public static final Vector4f WHITE = new Vector4f(1, 1, 1, 1);
    public static final Vector4f GREY  = new Vector4f(0.5f, 0.5f, 0.5f, 1);
    public static final Vector4f BLACK = new Vector4f(0, 0, 0, 1);
    public static final Vector4f RED =   new Vector4f(1, 0, 0, 1);
    public static final Vector4f GREEN = new Vector4f(0, 1, 0, 1);
    public static final Vector4f BLUE =  new Vector4f(0, 0, 1, 1);

    public static Vector4f changeAlpha(Vector4f color, float opacity){
        return new Vector4f(color.x, color.y, color.z, opacity);
    }
}
