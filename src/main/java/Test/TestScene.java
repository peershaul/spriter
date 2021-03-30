package Test;


import Scenes.Camera;
import Scenes.Scene;
import components.GameObject;
import components.ObjectRenderer;
import graphics.Renderer;
import graphics.Shader;
import graphics.Texture;
import org.joml.Vector2f;
import utils.AssetPool;
import utils.KeyListener;

import java.security.Key;

import static org.lwjgl.glfw.GLFW.*;

public class TestScene extends Scene {

    public TestScene(){
        super("Test Scene");
        reset();
    }

    private Camera cam;
    private Shader shader;
    private float camMoveVel = 450;
    private final float camAccel = 20;
    

    @Override
    public void init(){
        shader = AssetPool.getShader("resources/shaders/test.glsl");
        Texture texture = AssetPool.getTexture("resources/textures/mario.png");
        cam = new Camera(new Vector2f());

        addRendererToScene(new Renderer(shader, new int[]{ 2, 2 }));




        Texture.assign(0, texture);
        shader.uploadTexture("Texture", 0);
        shader.uploadMat4f("projection", cam.getProjection());
        shader.uploadMat4f("viewMat", cam.getView());
    }

    @Override
    public void awake(){

        GameObject gameObject = new GameObject("player");

        gameObject.addComponent(new ObjectRenderer());

        addGameObjectToScene(gameObject);
        setupGameObject(gameObject);
        renderers.get(0).addObjectRenderer(gameObject.getComponent(ObjectRenderer.class));
    }

    private void setupGameObject(GameObject gameObject){
        ObjectRenderer obr = gameObject.getComponent(ObjectRenderer.class);
        obr.vertexData = new float[]{
                // Position     Coords
                100,   0,       1, 1,
                  0, 100,       0, 0,
                100, 100,       1, 0,
                  0,   0,       0, 1
        };

        obr.indexData = new int[]{
                2, 1, 0,
                3, 1, 0
        };
    }

    @Override
    public void update(float dt) {

        if(KeyListener.isKeyPressed(GLFW_KEY_UP) && !KeyListener.isKeyPressed(GLFW_KEY_DOWN))
            camMoveVel += camAccel * dt;
        else if(KeyListener.isKeyPressed(GLFW_KEY_DOWN) && !KeyListener.isKeyPressed(GLFW_KEY_UP))
            camMoveVel += -camAccel * dt;

        System.out.println("Camera Velocity: " + camMoveVel);
        System.out.println("Camera Position: " + cam.position);

        if(KeyListener.isKeyPressed(GLFW_KEY_A) && !KeyListener.isKeyPressed(GLFW_KEY_D))
            cam.position.x += -camMoveVel * dt;
        else if (!KeyListener.isKeyPressed(GLFW_KEY_A) && KeyListener.isKeyPressed(GLFW_KEY_D))
            cam.position.x += camMoveVel * dt;

        if(KeyListener.isKeyPressed(GLFW_KEY_W) && !KeyListener.isKeyPressed(GLFW_KEY_S))
            cam.position.y += camMoveVel * dt;

        else if(!KeyListener.isKeyPressed(GLFW_KEY_W) && KeyListener.isKeyPressed(GLFW_KEY_S))
            cam.position.y += -camMoveVel * dt;

        shader.uploadMat4f("projection", cam.getProjection());
        shader.uploadMat4f("viewMat", cam.getView());

        for(GameObject go : gameObjects) go.update(dt);
        for(Renderer rend : renderers) rend.draw();
    }

    @Override
    public void SceneUpdate(float dt) {

    }
}