package utils;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Color {

    public static Vector3f normalize(Vector3f ogColor) { return new Vector3f(ogColor.x, ogColor.y, ogColor.z).div(255); }
    public static Vector4f normalize(Vector4f ogColor){
        Vector3f res = normalize(new Vector3f(ogColor.x, ogColor.y, ogColor.z));
        return new Vector4f(res.x, res.y, res.z, ogColor.w);
    }
}
