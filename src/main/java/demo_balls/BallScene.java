package demo_balls;

import Scenes.Scene;
import components.FrameManager;
import components.Sprite;
import components.Transform;
import components.frames.RectFrame;
import components.shape.Rectangle;
import graphics.Renderer;
import org.joml.Vector2f;
import utils.AssetPool;
import utils.Color;
import utils.Window;

import static org.lwjgl.opengl.GL11.*;


public class BallScene extends Scene {

    public BallScene(){
        super("balls collision scene");
        screen = Window.getScreen();
    }

    private Vector2f screen;
    private float boxSize;
    private final float box_border_width = 5;

    @Override
    public void init(){
        createBox();
    }

    private void createBox(){
        addRendererToScene(new Renderer(
                AssetPool.getShader("resources/shaders/test.glsl"),
                new int[] { 2, 4 },
                true
        ));

        getRendererShader(0).uploadVec2f("screen", screen);

        float size = (float) (Math.min(screen.x, screen.y) * 0.95);

        FrameManager frame = new FrameManager(new RectFrame(new Transform(
                new Vector2f(0),
                new Vector2f(size)
        )));

        addToRenderer(0, frame);
        renderers.get(0).setDrawMode(GL_LINE_LOOP);
    }

    @Override
    public void SceneUpdate(float dt) {

    }
}
