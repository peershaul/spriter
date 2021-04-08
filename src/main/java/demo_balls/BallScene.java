package demo_balls;

import Scenes.Scene;
import components.Sprite;
import components.Transform;
import components.shape.Rectangle;
import graphics.Renderer;
import org.joml.Vector2f;
import utils.AssetPool;
import utils.Color;
import utils.Window;



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
                false
        ));

        getRendererShader(0).uploadVec2f("screen", screen);

        boxSize = Math.min(screen.y, screen.x) * 0.9f;

        Transform boxOuterTrans = new Transform(
                new Vector2f(0),
                new Vector2f(boxSize + box_border_width)
        );

        Sprite outerBoxSpr = new Sprite(new Rectangle(boxOuterTrans));
        outerBoxSpr.color = Color.GREY;
        outerBoxSpr.zIndex = 0;

        addToRenderer(0, outerBoxSpr);

        Transform boxInnerTrans = new Transform(new Vector2f(0), new Vector2f(boxSize));
        Sprite innerBoxSpr = new Sprite(new Rectangle(boxInnerTrans));
        innerBoxSpr.color = Color.BLACK;
        innerBoxSpr.zIndex = 1;

        addToRenderer(0, innerBoxSpr);
    }

    @Override
    public void SceneUpdate(float dt) {

    }
}
