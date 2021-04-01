package platformer_demo;

import Scenes.Scene;
import graphics.Renderer;
import utils.AssetPool;

public class PlatformerScene extends Scene {

    public PlatformerScene(){
        super("platformer scene");
    }

    @Override
    public void init(){
        addRendererToScene(new Renderer(
                AssetPool.getShader("resources/shaders/platformer/background.glsl"),
                new int[]{ 2, 4 }
        ));
    }

    @Override
    public void SceneUpdate(float dt) {

    }
}
