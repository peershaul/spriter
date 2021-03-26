import example_chess.ChessScene;
import Scenes.Scene;
import Test.TestScene;
import example_platformer.PlatformerScene;
import utils.Window;

public class Main{
    public static void main(String[] args){
        Window win = Window.start(1280, 720, "Chess game");

        Scene.addScene(new Scene[]{ new ChessScene(), new PlatformerScene() });
        Scene.setCurrentScene(1);

        win.loop();
    }
}