package Scenes;

import components.GameObject;
import graphics.Renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Scene {
    private static List<Scene> scenes = new ArrayList<>();
    private static Scene currentScene = null;

    public static List<Scene> getScenes() { return scenes; }
    public static void addScene(Scene[] new_scenes){
        Collections.addAll(scenes, new_scenes);
    }

    public static void setCurrentScene(int index){

        if(index >= scenes.size()){
            System.out.println("Scene does not found");
            return;
        }

        if(currentScene != scenes.get(index)) {
            if(currentScene != null) {
                currentScene.reset();
                currentScene.isActive = false;
            }
            currentScene = scenes.get(index);
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

    public void update(float dt){
        SceneUpdate(dt);
        for(GameObject go : gameObjects) go.update(dt);
        for(Renderer rend : renderers) rend.draw();

    }

    public abstract void SceneUpdate(float dt);
}
