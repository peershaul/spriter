import Test.SecondTestScene;

import Scenes.Scene;

import utils.Window;

public class Main{
    public static void main(String[] args){
        Window win = Window.start(1280, 720, "Chess game");

        Scene.addScene(new Scene[]{
                new SecondTestScene()
        });
        Scene.setCurrentScene(0);

        win.loop();
    }
}