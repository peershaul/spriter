package demo_balls;

import Scenes.Scene;
import components.FrameManager;
import components.GameObject;
import components.Sprite;
import components.Transform;
import components.frames.RectFrame;
import components.shape.Rectangle;
import graphics.Line;
import graphics.Renderer;
import org.joml.Random;
import org.joml.Vector2f;
import org.joml.Vector4f;
import utils.AssetPool;
import utils.Color;
import utils.Window;

import static org.lwjgl.opengl.GL11.*;


public class BallScene extends Scene {

    public BallScene(){
        super("balls collision scene");
        screen = Window.getScreen();
        rand = new Random();
    }

    private final Vector2f screen;
    public BallObject[] balls;
    private final Random rand;

    private Line[] grid;

    @Override
    public void init(){
        createBox();
        createBalls();
    }

    @Override
    public void awake(){
        for(BallObject ball : balls) {
            addToRenderer(1, ball);
        }
    }

    private void createBox(){
        addRendererToScene(new Renderer(
                AssetPool.getShader("resources/shaders/balls/box.glsl"),
                new int[] { 2, 4 },
                true
        ));

        getRendererShader(0).uploadVec2f("screen", screen);

        float size = (float) (Math.min(screen.x, screen.y) * 0.95);

        BallObject.frameSize = size / 2;

        FrameManager frame = new FrameManager(new RectFrame(new Transform(
                new Vector2f(0),
                new Vector2f(size)
        )));

        addToRenderer(0, frame);
        renderers.get(0).setDrawMode(GL_LINE_LOOP);
    }

    private void createBalls(){
        addRendererToScene(new Renderer(
                AssetPool.getShader("resources/shaders/balls/box.glsl"),
                new int[] { 2, 4 },
                false
        ));

        getRendererShader(1).uploadVec2f("screen", screen);

        int amount = 20;
        balls = new BallObject[amount];

        float randMulti = 200;

        for(int i = 0; i < amount; i++) {
            balls[i] = new BallObject(
                    i, new Transform(
                        new Vector2f(
                                -BallObject.frameSize + (rand.nextFloat() * 2 * BallObject.frameSize),
                                -BallObject.frameSize + (rand.nextFloat() * 2 * BallObject.frameSize)
                        )
                    ),
                    new Vector2f(
                            -(randMulti / 2) + (rand.nextFloat() * randMulti),
                            -(randMulti / 2) + (rand.nextFloat() * randMulti)
                    ),
                    new Vector4f(
                            rand.nextFloat(),
                            rand.nextFloat(),
                            rand.nextFloat(),
                            1
                    ));
            addGameObjectToScene(balls[i]);
        }

        BallObject.balls = balls;

        int factor = 4;
        float[] limits = BallObject.gridSort(factor);

        grid = new Line[factor * 2];
        for(int i = 0; i < factor; i++)
            grid[i] = new Line(
                    new Vector2f(BallObject.frameSize, limits[i]),
                    new Vector2f(-BallObject.frameSize, limits[i])
            );

        for(int i = factor; i < factor * 2; i++)
            grid[i] = new Line(
                    new Vector2f(limits[i - factor], BallObject.frameSize),
                    new Vector2f(limits[i - factor], -BallObject.frameSize)
            );

    }

    @Override
    public void drawUI(){
        for(Line l : grid) l.draw(Line.DrawScale.PIXELS);
    }

    //private void drawGrid()

    @Override
    public void SceneUpdate(float dt) {

    }
}
