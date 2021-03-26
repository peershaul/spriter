package example_platformer;

import Scenes.Scene;
import components.GameObject;
import components.ObjectRenderer;
import components.Rectangle;
import components.Transform;
import graphics.Renderer;
import math.Vector2f;
import math.Vector3f;
import utils.AssetPool;
import utils.Window;

public class PlatformerScene extends Scene {
    public PlatformerScene() {
        super("Platformer scene");
        reset();
    }

    @Override
    public void init(){

        createBackground();
        addRendererToScene(new Renderer(
                AssetPool.getShader("resources/shaders/platformer/background.glsl"),
                new int[] { 2, 3 }
                ));

        PlatformerPlayer player = new PlatformerPlayer();
        addGameObjectToScene(player);

        renderers.get(1).addObjectRenderer(player.getComponent(ObjectRenderer.class));

    }

    private void createBackground(){
        addRendererToScene(new Renderer(AssetPool.getShader("resources/shaders/platformer/background.glsl"), new int[]{ 2, 3 }));

        Vector2f screen = Window.getScreen();
        renderers.get(0).getShader().uploadVec2f("screen", screen);

        GameObject sky = new GameObject("sky",
                new Transform(
                        new Vector2f(0.5f, 0.55f).mul(screen),
                        new Vector2f(1, 0.9f).mul(screen)
                ));

        Rectangle skyObr = new Rectangle();
        skyObr.changeColor(new Vector3f(51, 204, 255).normalize());
        sky.addComponent(skyObr);

        addGameObjectToScene(sky);

        skyObr.calc();

        GameObject ground = new GameObject("ground",
                new Transform(
                        new Vector2f(0.5f, 0.05f).mul(screen),
                        new Vector2f(1, 0.1f).mul(screen)
                ));

        Rectangle groundObr = new Rectangle();
        groundObr.changeColor(new Vector3f(0, 153, 51).normalize());
        ground.addComponent(groundObr);

        groundObr.calc();

        addGameObjectToScene(ground);

        renderers.get(0).addObjectRenderer(skyObr);
        renderers.get(0).addObjectRenderer(groundObr);
    }

    @Override
    public void reset(){

    }

    @Override
    public void update(float dt) {
        for(GameObject go : gameObjects) go.update(dt);
        for(Renderer rend : renderers) rend.draw();
    }
}
