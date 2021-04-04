package platformer_demo;

import Scenes.Scene;
import components.Sprite;
import components.Transform;
import components.shape.Rectangle;
import graphics.Renderer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import utils.AssetPool;
import utils.KeyListener;
import utils.Window;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static utils.Color.normalize;

public class PlatformerScene extends Scene {

    public PlatformerScene(){
        super("platformer scene");
    }

    private float renderModeTimer = 0;
    private PlatformerPlayer player;

    @Override
    public void init(){
        addRendererToScene(new Renderer(
                AssetPool.getShader("resources/shaders/platformer/background.glsl"),
                new int[]{ 2, 4 }
        ));

        setBackground();

        addRendererToScene(new Renderer(
                AssetPool.getShader("resources/shaders/platformer/player.glsl"),
                new int[]{2, 4}
        ));

        renderers.get(1).getShader().uploadVec2f("screen", Window.getScreen());

        player = new PlatformerPlayer();

    }

    @Override
    public void awake(){
        addGameObjectToScene(player);
        addToRenderer(1, player);
    }

    private void setBackground(){
        Sprite sky = new Sprite(new Rectangle(new Transform(
                new Vector2f(0, 0.05f),
                new Vector2f(1, 0.9f)
        )));

        sky.color = normalize(new Vector4f(66,135,245, 1));

        Sprite ground = new Sprite(new Rectangle(new Transform(
                new Vector2f(0, -0.45f),
                new Vector2f(1, 0.1f)
        )));

        ground.color = normalize(new Vector4f(0,123,51,1));

        addToRenderer(0, sky);
        addToRenderer(0, ground);
    }

    @Override
    public void SceneUpdate(float dt) {
        if(KeyListener.isKeyPressed(GLFW_KEY_L) && renderModeTimer >= 0.5f){
            renderModeTimer = 0;
            for(Renderer rend : renderers) rend.toggleWireframe();
        }

        // System.out.println(renderers.get(1).getDump(false));

        renderModeTimer += dt;
    }

}
