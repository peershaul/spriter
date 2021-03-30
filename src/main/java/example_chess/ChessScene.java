package example_chess;

import Scenes.Scene;
import components.GameObject;
import components.ObjectRenderer;
import graphics.Renderer;
import utils.AssetPool;
import utils.Window;

import java.util.ArrayList;

public class ChessScene extends Scene {

    public ChessScene() {
        super("Chess");
    }

    private Renderer renderer;

    @Override
    public void init(){
        renderer = new Renderer(
                AssetPool.getShader("resources/shaders/board.glsl"),
                new int[]{ 2, 3 }
        );

        addRendererToScene(renderer);

        renderer.getShader().uploadVec2f("screen", Window.getScreen());

        ChessBoard board = new ChessBoard();
        addGameObjectToScene(board);

        // Window.get().changeClearColor(new Vector4f(1, 1, 1, 1));
    }

    @Override
    public void awake(){
        for(GameObject go : gameObjects) renderer.addObjectRenderer(go.getComponent(ObjectRenderer.class));
    }

    @Override
    public void reset(){
        renderer = null;
        gameObjects = new ArrayList<>();
        renderers = new ArrayList<>();
    }

    @Override
    public void update(float dt) {
        for(GameObject go : gameObjects) go.update(dt);
        for(Renderer rend : renderers) rend.draw();
    }

    @Override
    public void SceneUpdate(float dt) {

    }
}