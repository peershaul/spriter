package utils;

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance = null;
    private Vector2f scroll, pos, last_pos;
    private boolean[] mouseButtons = new boolean[3];
    private boolean isDragging;

    private MouseListener(){
        this.scroll = new Vector2f();
        this.pos = new Vector2f();
        this.last_pos = new Vector2f();
    }

    public static MouseListener get(){
        if(instance == null)
            instance = new MouseListener();

        return instance;
    }

    public static void mousePosCallback(long window, double xpos, double ypos){
        get().last_pos = new Vector2f(get().pos.x, get().pos.y);
        get().pos = new Vector2f((float) xpos, (float) ypos);
        boolean dragging = false;
        for(int i = 0; i < get().mouseButtons.length; i++)
            if(get().mouseButtons[i]){
                dragging = true;
                break;
            }
        get().isDragging = dragging;
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods){
        if(button < get().mouseButtons.length) {
            if (action == GLFW_PRESS)
                get().mouseButtons[button] = true;
            else if (action == GLFW_RELEASE) {
                get().mouseButtons[button] = false;
                get().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset){
        get().scroll = new Vector2f((float) xOffset, (float) yOffset);
    }

    public static void endFrame(){
        get().scroll = new Vector2f();
        get().last_pos = new Vector2f(get().pos.x, get().pos.y);
    }

    public static Vector2f getPos(){ return get().pos; }
    public static Vector2f getDeltaPos(){ return get().last_pos.sub(get().pos); }
    public static Vector2f getScroll(){ return get().scroll; }
    public static boolean isDragging(){ return get().isDragging; }

    public static boolean mouseButtonDown(int button){
        if(button >= get().mouseButtons.length) return false;
        return get().mouseButtons[button];
    }
}
