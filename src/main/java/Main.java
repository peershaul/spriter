import utils.Window;

public class Main{
    public static void main(String args[]){
        Window win = Window.start(640, 480, "Hello World");
        win.loop();
    }
}