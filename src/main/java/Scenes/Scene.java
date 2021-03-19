package Scenes;

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
        if(currentScene == scenes[index])
            currentScene = scenes[index];
    }

    public static Scene getCurrentScene(){ return currentScene; }


    private final String name;

    public Scene(String name){
        this.name = name;
    }

    public void init(){

    }

    public abstract void update(float dt);
}