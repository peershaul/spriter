package Test;


import Scenes.Scene;
import components.GameObject;
import components.ObjectRenderer;
import example_chess.ChessBoard;
import graphics.Renderer;
import graphics.Shader;
import math.Vector4f;
import utils.AssetPool;
import utils.Window;

public class TestScene extends Scene {

    public TestScene(){
        super("TestScene");
    }

    private Renderer renderer;

    @Override
    public void init(){
        Shader shader = AssetPool.getShader("resources/shaders/board.glsl");

        shader.uploadVec2f("screen", Window.getScreen());

        renderer = new Renderer(shader, new int[]{ 2, 3 });
        addRendererToScene(renderer);
        ChessBoard board = new ChessBoard();
        addGameObjectToScene(board);

        TestGameObject test = new TestGameObject();
        addGameObjectToScene(test);

        Window.get().changeClearColor(new Vector4f());
    }

    @Override
    public void awake(){
        for(GameObject go : gameObjects) renderer.addObjectRenderer(go.getComponent(ObjectRenderer.class));
    }

    @Override
    public void update(float dt) {
        for(GameObject go : gameObjects) go.update(dt);
        for(Renderer renderer : renderers) renderer.draw();
    }
}
