package Test;

import Scenes.Scene;
import components.GameObject;
import components.ObjectRenderer;
import components.Transform;
import components.shapes.Circle;
import graphics.Renderer;
import graphics.Shader;
import org.joml.Vector2f;
import utils.AssetPool;
import utils.Window;

public class SecondTestScene extends Scene {

    public SecondTestScene(){
        super("second test scene");
    }

    private Shader shader;
    private GameObject circObject;

    @Override
    public void init(){
        shader = AssetPool.getShader("resources/shaders/basic.glsl");
        addRendererToScene(new Renderer(shader, new int[]{2, 3}));

        shader.uploadVec2f("screen", Window.getScreen());
    }

    @Override
    public void awake(){
        circObject = new GameObject(
                "circular game object",
                new Transform(new Vector2f(), new Vector2f(5))
        );

        Circle circComp = new Circle(200, 5);
        circObject.addComponent(circComp);

        addGameObjectToScene(circObject);

        renderers.get(0).addObjectRenderer(circObject.getComponent(ObjectRenderer.class));

        shader.uploadVec3f("Color", circComp.color);
    }

    @Override
    public void SceneUpdate(float dt) {

    }
}
