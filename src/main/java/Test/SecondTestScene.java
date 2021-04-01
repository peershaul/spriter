package Test;

import Scenes.Scene;
import components.GameObject;
import components.Sprite;
import components.Transform;
import components.shape.Rectangle;
import components.shape.Shape;
import graphics.ArrayBuffer;
import graphics.IndexBuffer;
import graphics.Shader;
import graphics.VertexBuffer;
import org.joml.Random;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import utils.AssetPool;
import utils.KeyListener;
import utils.Window;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SecondTestScene extends Scene {

    public SecondTestScene(){
        super("second test scene");
    }

    private ArrayBuffer arrayBuffer;
    private VertexBuffer vb;
    private Shader shader;
    private GameObject go;
    private Vector3f multi = new Vector3f(1);
    private float divider = 0.25f;

    @Override
    public void init(){
        go = new GameObject("Test object",
                new Transform(
                        new Vector2f(100),
                        new Vector2f(500)
                ));

        Sprite spr = new Sprite(new Rectangle(go.transform));

        go.addComponent(spr);

        float[] data = spr.getVertexData();

        for(int i = 0; i < spr.shape.getVertexCount(); i++)
            System.out.println(data[i * 2] + "\t" + data[i * 2 + 1]);

        vb = new VertexBuffer();
        vb.putData(spr.getVertexData(), true);

        IndexBuffer ib = new IndexBuffer();
        ib.putData(spr.getIndexData());

        arrayBuffer = new ArrayBuffer(vb, ib, new int[]{ 2, 4 });
        shader = AssetPool.getShader("resources/shaders/test.glsl");

        shader.uploadVec2f("screen", Window.getScreen());

        Window.get().changeClearColor(new Vector4f(0));
    }

    @Override
    public void update(float dt){
        this.SceneUpdate(dt);

       shader.bind();
       arrayBuffer.bind();

        glDrawElements(GL_TRIANGLES, arrayBuffer.getIndexBuffer().getAmount(), GL_UNSIGNED_INT, NULL);

        arrayBuffer.unbind();
        shader.unbind();
    }

    @Override
    public void SceneUpdate(float dt) {
        Random rand = new Random();

        Sprite spr = go.getComponent(Sprite.class);

        Vector4f c = spr.color;

        if(c.x >= 1 || c.x <= 0)
            multi.x *= -1;
        if(c.y >= 1 || c.y <=0 )
            multi.y *= -1;
        if(c.z >= 1 || c.z <= 0)
            multi.z *= -1;

        spr.color = new Vector4f(
                c.x + (multi.x * dt *  rand.nextFloat() / divider),
                c.y + (multi.y * dt * rand.nextFloat() / divider),
                c.z + (multi.z * dt * rand.nextFloat() / divider),
                1
        );

        vb.putData(spr.getVertexData(), true);
    }
}