package Scenes;

import graphics.*;
import math.Vector2f;
import math.Vector4f;
import utils.Window;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ChessScene extends Scene {

    public ChessScene() {
        super("Chess scene");
    }

    private Mesh board;
    private ArrayBuffer vao;
    private Shader shader;

    @Override
    public void init() {
        float[] data = new float[100 * 4 * 4];
        int[] indices = new int[100 * 6];
        Vector2f[] colors = new Vector2f[] {
                new Vector2f(1, 0),
                new Vector2f(0, 1)
        };
        int color = 0;
        int index = 0, vertex = 0, i_index = 0;
        for (int y = 0; y < 10; y++){
            for (int x = 0; x < 10; x++) {
                data[index + 0] = (x * 100) - 500;
                data[index + 1] = (y * 100) - 400;
                data[index + 2] = colors[color].x;
                data[index + 3] = colors[color].y;

                data[index + 4] = (x * 100) - 400;
                data[index + 5] = (y * 100) - 500;
                data[index + 6] = colors[color].x;
                data[index + 7] = colors[color].y;

                data[index + 8] = (x * 100) - 500;
                data[index + 9] = (y * 100) - 500;
                data[index + 10] = colors[color].x;
                data[index + 11] = colors[color].y;

                data[index + 12] = (x * 100) - 400;
                data[index + 13] = (y * 100) - 400;
                data[index + 14] = colors[color].x;
                data[index + 15] = colors[color].y;

                index += 16;

                indices[i_index + 0] = vertex + 2;
                indices[i_index + 1] = vertex + 1;
                indices[i_index + 2] = vertex + 0;
                indices[i_index + 3] = vertex + 0;
                indices[i_index + 4] = vertex + 1;
                indices[i_index + 5] = vertex + 3;

                vertex += 4;
                i_index += 6;


                color = (color == 0)? 1 : 0;

            }
        }

        /*for(int i = 0; i < data.length; i += 4)
            System.out.println("X: " + data[i] + "\nY: " + data[i + 1] + "\nR: " + data[i + 2] + "\nG: " + data[i + 3]);

        for(int i = 0; i < indices.length; i += 3)
            System.out.println(indices[i] + ", " + indices[i + 1] + ", " + indices[i + 2]);*/

        VertexBuffer vb = new VertexBuffer();
        vb.putData(data, false);

        IndexBuffer ib = new IndexBuffer();
        ib.putData(indices);

        vao = new ArrayBuffer(vb, ib, new int[]{2, 2});
        shader = new Shader("resources/shaders/board.glsl");
        shader.Compile();

        shader.uploadVec2f("screen", new Vector2f(Window.get().width, Window.get().height));

        Window.get().changeClearColor(new Vector4f(1, 1,1,1));
    }


    @Override
    public void update(float dt) {
        shader.bind();
        vao.bind();


        glDrawElements(GL_TRIANGLES, vao.getIndexBuffer().getAmount(), GL_UNSIGNED_INT, NULL);

        int err = glGetError();
        while(err != 0 || err != GL_FALSE) {
            System.out.println("OPENGL ERROR ( " + err + " )");
            err = glGetError();
        }

        vao.unbind();
        shader.unbind();
    }
}
