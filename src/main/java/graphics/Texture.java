package graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.w3c.dom.Text;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {

    public static Map<Integer, Texture> assigned = new HashMap<>();

    public static boolean assign(int slot, Texture tex){
            if(assigned.containsValue(tex)){
               System.out.println("Texture is already assigned");
               return false;
            }

            if(assigned.containsKey(slot)){
               System.out.println("slot is already assigned");
               return false;
            }

            assigned.put(slot, tex);
            tex.slot = slot;
            // tex.unbind();
            tex.bind();
            glActiveTexture(GL_TEXTURE0 + slot);
            return true;
    }

    public static void resign(Texture tex){
        assigned.remove(tex.getSlot());
        tex.slot = -1;
    }

    public static void resign(int slot){
        Texture tex = assigned.get(slot);
        tex.slot = -1;
        assigned.remove(slot);
    }


    private final String filepath;
    private final int id;
    private int slot = -1;

    public Texture(String filepath){
        this.filepath = filepath;

        id = glGenTextures();
        bind();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

        if(image != null) {
            if (channels.get(0) == 3 || channels.get(0) == 4)
                glTexImage2D(GL_TEXTURE_2D, 0, (channels.get(0) == 3) ? GL_RGB : GL_RGBA, width.get(0), height.get(0),
                        0, (channels.get(0) == 3) ? GL_RGB : GL_RGBA, GL_UNSIGNED_BYTE, image);
            else assert false : "Error Unknown channel amount '" + filepath + "'";
        }
        else assert false : "Cannot load the texture '" + filepath + "'";

        stbi_image_free(image);

        unbind();
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getSlot() { return slot; }
}
