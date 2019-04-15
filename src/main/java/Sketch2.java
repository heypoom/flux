/*
Mutable ripples

Controls:
	- Move the mouse to rotate the camera.

Author:
  Jason Labbe

Site:
  jasonlabbe3d.com
*/

import processing.core.PApplet;

public class Sketch2 extends PApplet {
    private int cubeSize = 130;
    private int blockSize = 20;

    public void settings() {
        size(700, 700, P3D);
    }

    public void setup() {
        colorMode(HSB, 255);
    }

    public void draw() {
        background(0);

        noStroke();

        // Center the scene and map the mouse's input to rotate the camera.
        translate(width / 2, height / 2, 200);
        rotateX(radians(map(mouseY, 0, height, 180, -180)));
        rotateY(radians(map(mouseX, 0, width, -180, 180)));

        // Translate the center of the ripple effect through the cube.
        float noiseMult = 0.01f;
        float nx = noise(frameCount * noiseMult) * cubeSize;
        float ny = noise(1000 + frameCount * noiseMult) * cubeSize;
        float nz = noise(5000 + frameCount * noiseMult) * cubeSize;

        int maxCount = cubeSize - (cubeSize % blockSize);

        // We're building a cube of spheres so we need to loop through all three x, y, and z axes.
        for (int x = 0; x <= cubeSize; x += blockSize) {
            for (int y = 0; y <= cubeSize; y += blockSize) {
                for (int z = 0; z <= cubeSize; z += blockSize) {
                    // Skip inside for faster performance.
                    if (x != 0 && x != maxCount && y != 0 && y != maxCount && z != 0 && z != maxCount) {
                        continue;
                    }

                    // Define what offset in time each sphere will be in by using their position.
                    float mult = dist(x, y, z, nx, ny, nz);
                    float offset = (frameCount + mult) * 0.1f;
                    float wave = sin(offset);

                    pushMatrix();

                    // Move the sphere in place.
                    translate(x - cubeSize / 2, y - cubeSize / 2, z - cubeSize / 2);
                    scale(wave);

                    // The sphere's position offsets its hue.
                    // Use its wave value to determine its brightness.
                    float colorHue = (frameCount + mult * 0.5f) % 255;
                    float colorBright = map(wave, -1, 1, 255, 0);
                    fill(colorHue, 255, colorBright);

                    sphere(blockSize * 0.8f);

                    popMatrix();
                }
            }
        }
    }
}
