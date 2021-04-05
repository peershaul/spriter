package Test;

import Scenes.Scene;
import components.Sprite;
import components.Transform;
import components.shape.Circle;
import components.shape.Shape;
import graphics.Renderer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.system.CallbackI;
import utils.AssetPool;
import utils.KeyListener;
import utils.Window;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static utils.Color.normalize;

public class TestScene extends Scene {

    public TestScene(){
        super("circle test scene");
    }

    float timer = 0;
    float velocity = 0.05f;
    Vector4f targetColor = new Vector4f();

    @Override
    public void init(){
        Transform circTransform = new Transform(new Vector2f(0));
        float radius = 500;
        int resolution = 40;
        Shape circ = new Circle(radius, resolution, circTransform);

        Sprite spr = new Sprite(circ);
        spr.color = normalize(new Vector4f(30, 50, 203, 0.4f));

        addRendererToScene(new Renderer(
                AssetPool.getShader("resources/shaders/test.glsl"),
                new int[] { 2, 4 }
                ));
        addToRenderer(0, spr);
        getRendererShader(0).uploadVec2f("screen", Window.getScreen());
    }

    @Override
    public void SceneUpdate(float dt) {

        timer += dt;

        if(KeyListener.isKeyPressed(GLFW_KEY_L) && timer >= 0.25f){
            for(Renderer rend : renderers) rend.toggleWireframe();
            timer = 0;
        }
    }

}