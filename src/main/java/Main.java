import Test.SecondTestScene;

import Scenes.Scene;

import platformer_demo.PlatformerScene;
import utils.Window;

public class Main{
    public static void main(String[] args){
        Window win = Window.start(1280, 720, "Spriter game engine");

        Scene.addScene(new Scene[]{
                new PlatformerScene(),
                new SecondTestScene()
        });
        Scene.setCurrentScene(0);

        win.loop();
    }
}