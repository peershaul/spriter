package utils;

import graphics.Shader;
import graphics.Texture;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();

    public static Shader getShader(String filepath){
        if(shaders.containsKey(filepath)) return shaders.get(filepath);
        Shader shader = new Shader(filepath);
        shader.Compile();
        shaders.put(filepath, shader);
        return shader;
    }

    public static Texture getTexture(String filepath){
        if(textures.containsKey(filepath)) return textures.get(filepath);
        Texture tex = new Texture(filepath);
        textures.put(filepath, tex);
        return tex;
    }
}
