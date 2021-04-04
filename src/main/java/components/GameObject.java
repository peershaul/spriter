package components;

import Scenes.Scene;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    protected final String name;
    public Transform transform;
    public Scene scene;
    protected List<Component> components;

    public GameObject(String name){
        this.name = name;
        this.transform = new Transform();
        components = new ArrayList<>();
    }

    public GameObject(String name, Transform transform){
        this.name = name;
        this.transform = transform;
        components = new ArrayList<>();
    }

    public <T extends Component> T getComponent(Class<T> compClass){
        for(Component c : components)
            if(compClass.isAssignableFrom(c.getClass())){
                try {
                    return compClass.cast(c);
                }
                catch(ClassCastException e){
                    e.printStackTrace();
                    assert false : "Error Casting Component";
                }
            }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> compClass){
        for(int i = 0; i < components.size(); i++){
            Component c = components.get(i);
            if(compClass.isAssignableFrom(c.getClass())){
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c){
        this.components.add(c);
        c.gameObject = this;
    }

    public String getName(){ return name; }
    public String toString(){ return "GameObject: " + name + "\nPosition: " + transform.position + "\nScale: " + transform.scale;}

    public void update(float dt){
        ObjectUpdate(dt);
        for (Component component : components) component.update(dt);
    }

    public void ObjectUpdate(float dt){

    }

    public void start(){
        for (Component component : components) component.start();
    }

    public void init(){

    }

}
