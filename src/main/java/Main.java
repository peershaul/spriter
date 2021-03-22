import utils.Window;

public class Main{
    public static void main(String args[]){
        Window win = Window.start(1280, 720, "Chess game");
        win.loop();
    }
}