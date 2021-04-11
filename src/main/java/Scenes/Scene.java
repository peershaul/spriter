package Scenes;

import components.DrawingElement;
import components.GameObject;
import components.Sprite;
import graphics.Renderer;
import graphics.Shader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Scene {
    private static List<Scene> scenes = new ArrayList<>();
    private static Scene currentScene = null;

    public static List<Scene> getScenes()  { return scenes; }
    public static void addScene(Scene[] new_scenes){
        Collections.addAll(scenes, new_scenes);
    }

    public static void setCurrentScene(int index){

        if(index >= scenes.size()){
            System.out.println("Scene does not found");
            return;
        }

        if((!scenes.get(index).isActive) &&
                (currentScene == null || !currentScene.equals(scenes.get(index)))){
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
        if((!scene.isActive) && (currentScene == null || !currentScene.equals(scene))){
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
        renderers = new ArrayList<>();
        gameObjects = new ArrayList<>();
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
        for(int i = 0; i < renderers.size(); i++) {
            if(!renderers.get(i).draw());// System.out.println("Problem on renderer number " + i);
        }

    }

    public void addToRenderer(int randrIndex, GameObject go){
        if(randrIndex >= renderers.size()){
            System.out.println("There is no renderer in slot " + randrIndex);
            return;
        }

        renderers.get(randrIndex).addSprite(go);
    }

    public void addToRenderer(int randrIndex, DrawingElement spr){
        if(randrIndex >= renderers.size()){
            System.out.println("There is no renderer in slot" + randrIndex);
            return;
        }

        renderers.get(randrIndex).addSprite(spr);
    }

    public Shader getRendererShader(int randrIndex){
        if(randrIndex >= renderers.size()) {
            System.out.println("There is no renderer in this slot " + randrIndex);
            return null;
        }

        return renderers.get(randrIndex).getShader();
    }

    public abstract void SceneUpdate(float dt);
}
