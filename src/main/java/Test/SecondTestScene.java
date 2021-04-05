package Test;

import Scenes.Scene;
import components.GameObject;
import components.Sprite;
import components.Transform;
import components.shape.Circle;
import components.shape.Rectangle;
import components.shape.Shape;
import graphics.*;
import org.joml.Random;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import utils.AssetPool;
import utils.KeyListener;
import utils.Window;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_MAX_TEXTURE_IMAGE_UNITS;
import static utils.Color.normalize;

public class SecondTestScene extends Scene {

    public SecondTestScene(){
        super("second test scene");
    }

    private ArrayBuffer arrayBuffer;
    private VertexBuffer vb;
    private Shader shader;
    private GameObject tester, go;
    private Vector3f multi = new Vector3f(1);
    private float divider = 1, velocity = 200, time = 10;

    @Override
    public void init(){

        addRendererToScene(new Renderer(
                AssetPool.getShader("resources/shaders/test.glsl"),
                new int[]{ 2, 4 }
        ));

        renderers.get(0).getShader().uploadVec2f("screen", Window.getScreen());

        go = new GameObject("Test object",
                new Transform(
                        new Vector2f(100),
                        new Vector2f(500)
                ));

        Sprite spr = new Sprite(new Rectangle(go.transform));
        spr.zIndex = 0;

        go.addComponent(spr);

        renderers.get(0).addSprite(go);
        addGameObjectToScene(go);

        tester = new GameObject(
                "test 2.0",
                new Transform(
                        new Vector2f(-100),
                        new Vector2f(300)
                )
        );

        Sprite anotherSpr = new Sprite(new Rectangle(tester.transform));

        anotherSpr.zIndex = -1;

        tester.addComponent(anotherSpr);
        renderers.get(0).addSprite(tester);
        addGameObjectToScene(tester);

        Sprite testSprite = new Sprite(new Rectangle(new Transform(
                new Vector2f(-200),
                new Vector2f(300)
        )));

        testSprite.zIndex = 2;
        testSprite.color = new Vector4f(0, 1, 0, 0.25f);

        addToRenderer(0, testSprite);

        Transform circTransform = new Transform(new Vector2f(0));
        float radius = 500;
        int resolution = 50;
        Shape circ = new Circle(radius, resolution, circTransform);

        Sprite circSpr = new Sprite(circ);
        circSpr.color = normalize(new Vector4f(30, 50, 203, 0.4f));

        addToRenderer(0, circSpr);
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
                c.x + (multi.x * dt * rand.nextFloat() / divider),
                c.y + (multi.y * dt * rand.nextFloat() / divider),
                c.z + (multi.z * dt * rand.nextFloat() / divider),
                1
        );

        if(KeyListener.isKeyPressed(GLFW_KEY_W))
            tester.transform.position.y += velocity * dt;

        else if(KeyListener.isKeyPressed(GLFW_KEY_S))
            tester.transform.position.y -= velocity * dt;

        if(KeyListener.isKeyPressed(GLFW_KEY_D))
            tester.transform.position.x += velocity * dt;

        else if(KeyListener.isKeyPressed(GLFW_KEY_A))
            tester.transform.position.x -= velocity * dt;

        time += dt;

        if(KeyListener.isKeyPressed(GLFW_KEY_L) && time >= 0.25f) {
            for (Renderer rend : renderers) rend.toggleWireframe();
            time = 0;
        }
    }
}