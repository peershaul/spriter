package Scenes;

import components.GameObject;
import graphics.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    private static Scene[] scenes = {};
    private static Scene currentScene = null;

    public static Scene[] getScenes() { return scenes; }
    public static void addScene(Scene[] new_scenes){
        Scene[] arr = new Scene[scenes.length + new_scenes.length];

        for(int i = 0; i < scenes.length; i++)
            arr[i] = scenes[i];

        for(int i = scenes.length; i < scenes.length + new_scenes.length; i++)
            arr[i] = new_scenes[i];

        scenes = arr;
    }

    public static void setCurrentScene(int index){
        if(currentScene != scenes[index]) {
            if(currentScene != null) {
                currentScene.reset();
                currentScene.isActive = false;
            }
            currentScene = scenes[index];
            currentScene.isActive = true;
            currentScene.init();
            currentScene.start();
        }
    }

    public static void setCurrentScene(Scene scene){
        if(currentScene != scene){
            if(currentScene != null) {
                currentScene.reset();
                currentScene.isActive = false;
            }
            currentScene = scene;
            currentScene.isActive = true;
            currentScene.init();
            currentScene.start();
        }
    }

    public static Scene getSceneByName(String name){
        for(Scene scene : scenes) {
            if (scene.name.equals(name))
                return scene;
        }
        return null;
    }

    public static Scene getCurrentScene(){ return currentScene; }

    protected final String name;
    protected boolean isActive;
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected List<Renderer> renderers = new ArrayList<>();

    public Scene(String name){
        this.name = name;
        reset();
    }

    public void start(){
        for (GameObject go : gameObjects) {
            go.init();
            go.start();
        }
        isActive = true;
        awake();
    }

    public void reset() {

    }

    public void awake(){

    }

    public void addGameObjectToScene(GameObject go){
        if(isActive){
            gameObjects.add(go);
            go.scene = this;
            go.init();
            go.start();
        }
        else {
            gameObjects.add(go);
            go.scene = this;
        }
    }

    public void addRendererToScene(Renderer renderer){
        for(Renderer rend : renderers) if(rend.equals(renderer)) return;

        renderers.add(renderer);
    }

    public void init(){

    }

    public abstract void update(float dt);
}
