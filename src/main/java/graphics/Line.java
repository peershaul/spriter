package graphics;

import org.joml.Vector2f;
import utils.Window;

import java.util.Vector;

import static org.lwjgl.opengl.GL11.*;

public class Line {

    public Vector2f start;
    public Vector2f end;

    public Line(Vector2f start, Vector2f end){
        this.start = start;
        this.end = end;
    }

    public void draw(DrawScale scale){

        Vector2f _start = new Vector2f(), _end = new Vector2f();

        switch(scale) {
            case DEFAULT:
                _start = new Vector2f(start);
                _end = new Vector2f(end);
                break;
            case PIXELS:
                Vector2f screen = Window.getScreen();
                _start = new Vector2f(
                        2 * start.x / screen.x,
                        2 * start.y / screen.y
                );
                _end = new Vector2f(
                        2 * end.x / screen.x,
                        2 * end.y / screen.y
                );
                break;
            default:
                assert false : "Error drawing a line";
        }

        glBegin(GL_LINES);
        glVertex2f(_start.x, _start.y);
        glVertex2f(_end.x, _end.y);
        glEnd();
    }

    public void update(Vector2f start, Vector2f end){
        this.start = start;
        this.end = end;
    }

    public enum DrawScale{ DEFAULT, PIXELS }

}
