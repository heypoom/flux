import processing.core.PApplet;
import processing.core.PShape;
import processing.opengl.PShader;

public class Sketch extends PApplet {
    private PShader colorShader;
    private PShape cube;
    private float angle;

    public void settings() {
        size(700, 700, P3D);
    }

    public void setup() {
        colorShader = loadShader("shader.frag");

        cube = createShape(BOX, 50);
        cube.setFill(color(80, 80, 80));
        cube.setStroke(color(255));
        cube.setStrokeWeight(10);

        stroke(22, 55, 22);
        rectMode(CENTER);
    }

    private void floatingPlane() {
        beginShape(QUADS);
        normal(0, 0, 1);

        fill(50, 50, 200);
        vertex(-100, +100);
        vertex(+100, +100);

        fill(200, 50, 50);
        vertex(+100, -100);
        vertex(-100, -100);
        endShape();
    }

    public void draw() {
//        filter(colorShader);
        background(255);
        rect(mouseX, mouseY, 150, 100);

        camera(width / 2, height / 2, 300,
                width / 2, height / 2, 0,
                0, 1, 0);

        pointLight(200, 200, 200, width / 2, height / 2, -200);

        translate(width / 2, height / 2);
        rotateY(angle);

        floatingPlane();

        shader(colorShader);
        shape(cube);

        translate(0, 200f, 0);

        angle += 0.01;
    }
}
