package utils;

import org.joml.Vector3f;

public class Color {

    public static Vector3f normalize(Vector3f ogColor) { return new Vector3f(ogColor.x, ogColor.y, ogColor.z).div(255); }
}
