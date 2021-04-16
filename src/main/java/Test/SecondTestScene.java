package Test;

import Scenes.Scene;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_LINES;

public class SecondTestScene extends Scene {

    public SecondTestScene(){
        super("second test scene");
    }

    @Override
    public void init(){

    }

    @Override
    public void update(float dt){
        glBegin(GL_LINES);
        glVertex2f(0.5f, 0);
        glVertex2f(-0.5f, 1);
        glEnd();

        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
    }

    @Override
    public void SceneUpdate(float dt) {

    }
}