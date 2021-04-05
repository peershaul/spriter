package utils;

import Scenes.Scene;
import graphics.ArrayBuffer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private static Window instance = null;

    public static Window start(int width, int height, String name){
        if(instance != null) {
            assert false : "There is already a window";
            return null;
        }

        instance = new Window(width, height, name);
        instance.start();
        return instance;
    }

    public static Window get(){
        assert instance != null : "The window hasn't started yet";
        return instance;
    }

    public static Vector2f getScreen(){ return new Vector2f(get().width, get().height); }

    private Window(int width, int height, String name){
        this.height = height;
        this.width = width;
        this.name = name;
    }

    private long window;
    public final int height, width;
    private final String name;
    private Vector4f clearColor;
    private boolean clearMod = false;

    private ArrayBuffer arrayBuffer;

    public void changeClearColor(Vector4f clearColor){
        if (!clearMod) clearMod = true;
        this.clearColor = clearColor;
    }

    private void start(){
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window won't be resizable

        // Create the window
        window = glfwCreateWindow(width, height, name, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetCursorPosCallback(window, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(window, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(window, KeyListener::KeyCallback);

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        if(!clearMod) clearColor = new Vector4f(0, 0, 0, 1);
    }

    public void loop() {

        float beginTime = (float) glfwGetTime();
        float endTime;
        float dt = -1.0f;

        // Set the clear color
        glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            if(Scene.getCurrentScene() == null)
                System.out.println("Current scene is null!");
            else if(dt >= 0)
                Scene.getCurrentScene().update(dt);


            if(KeyListener.isKeyPressed(GLFW_KEY_ESCAPE))
                glfwSetWindowShouldClose(window, true);


            for (int i = 0; i < Math.min(Scene.getScenes().size(), 9); i++)
                if (KeyListener.isKeyPressed(GLFW_KEY_1 + i)){
                    Scene.setCurrentScene(i);
                    break;
                }


            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            endTime = (float) glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
            System.out.println("FPS = " + ( 1 / dt));
        }
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
