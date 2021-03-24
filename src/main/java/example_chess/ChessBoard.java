package example_chess;

import components.GameObject;
import components.ObjectRenderer;
import components.Transform;

import math.Vector2f;
import math.Vector3f;
import math.Vector4f;
import utils.AssetPool;
import utils.Window;


public class ChessBoard extends GameObject {

    public ChessBoard() {
        super("chess board");
        this.transform = new Transform(
                new Vector2f(0),
                new Vector2f(1000)
        );
    }

    @Override
    public void init(){
        ObjectRenderer obr = new ObjectRenderer();
        obr.vertexData = calcVertexData();
        obr.indexData = calcIndexData(obr.vertexData);

        addComponent(obr);
    }

    @Override
    public void update(float dt){

    }

    private float[] calcVertexData(){
        Vector3f[] colors = new Vector3f[]{
                new Vector3f(210, 140,  69).normalize(),
                new Vector3f(255, 207, 159).normalize()
        };
        int color = 0;
        float[] data = new float[8 * 8 * 4 * (2 + 3)];
        int index = 0, divider = 8;
        float half = transform.scale.x / 2,
                unit = transform.scale.x / divider,
                step = half - unit;

        for(int y = 0; y < divider; y++){
            for(int x = 0; x < divider; x++){
                data[index + 0] = (x * unit) - half + transform.position.x;
                data[index + 1] = (y * unit) - step + transform.position.y;
                data[index + 2] = colors[color].x;
                data[index + 3] = colors[color].y;
                data[index + 4] = colors[color].z;

                data[index + 5] = (x * unit) - step + transform.position.x;
                data[index + 6] = (y * unit) - half + transform.position.y;
                data[index + 7] = colors[color].x;
                data[index + 8] = colors[color].y;
                data[index + 9] = colors[color].z;

                data[index + 10] = (x * unit) - half + transform.position.x;
                data[index + 11] = (y * unit) - half + transform.position.y;
                data[index + 12] = colors[color].x;
                data[index + 13] = colors[color].y;
                data[index + 14] = colors[color].z;

                data[index + 15] = (x * unit) - step + transform.position.x;
                data[index + 16] = (y * unit) - step + transform.position.y;
                data[index + 17] = colors[color].x;
                data[index + 18] = colors[color].y;
                data[index + 19] = colors[color].z;

                index += 20;
                color = (color == 0)? 1 : 0;
            }
            color = (color == 0)? 1 : 0;
        }
        return data;
    }

    private int[] calcIndexData(float[] vertexData) {
        int[] indices = new int[8 * 8 * 6];
        int index = 0;
        for(int i = 0; i < vertexData.length / (2 + 3); i += 4){
            indices[index + 0] = i + 2;
            indices[index + 1] = i + 1;
            indices[index + 2] = i + 0;

            indices[index + 3] = i + 0;
            indices[index + 4] = i + 1;
            indices[index + 5] = i + 3;

            index += 6;
        }
        return indices;
    }
}
