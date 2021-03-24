package utils;

import graphics.Shader;

import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static Map<String, Shader> shaders = new HashMap<>();

    public static Shader getShader(String filepath){
        if(shaders.containsKey(filepath)) return shaders.get(filepath);
        Shader shader = new Shader(filepath);
        shader.Compile();
        shaders.put(filepath, shader);
        return shader;
    }
}
