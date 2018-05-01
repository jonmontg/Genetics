import processing.core.*;

import java.util.Random;

public class Simulation {

    private Sketch p;

    private Population[] populations;
    private int simFrames;
    private int currFrame = 0;
    private double speedMult = 1;
    private PVector target;
    private boolean followMouse = false;

    public Simulation(Sketch p, int length, Population[] creatures) {
        this.p = p;
        this.target = new PVector(this.p.width / 2, 50);
        this.simFrames = length;
        this.populations = creatures;
    }

    public void update() {
        if (this.followMouse) {
            setTarget(this.p.mouseX, this.p.mouseY);
        }
        for (int frame = 0; frame < (int)this.speedMult; frame++) {
            this.currFrame++;
            for (Population p: this.populations) {
                p.update();
            }
        }
        if (this.currFrame >= simFrames) {
            this.currFrame = 0;
            for (Population p: this.populations) {
                p.nextGen();
                if (!this.followMouse) {
                    setTarget(new Random().nextInt(this.p.width), new Random().nextInt(this.p.height));
                }
            }
        }
    }

    public void draw() {
        p.background(100);
        p.fill(255, 255, 255);
        p.ellipse((int)this.target.x, (int)this.target.y, 20, 20);

        for (Population p: this.populations) {
            p.draw();
        }
    }

    public void setTarget(float x, float y) {
        this.target = new PVector(x, y);
        for (Population p: this.populations) {
            for (Creature c: p.getCreatures()) {
                c.setTarget(x, y);
            }
        }
    }

    public void scaleTime (double scale) {
        this.speedMult = this.speedMult * scale;
        if (this.speedMult < 1)
            this.speedMult = 1;
        else if (this.speedMult > this.simFrames)
            this.speedMult = this.simFrames;
    }

    public void keyPressed(char key) {
        System.out.println(key);
        if (key == 'z') {
            scaleTime(-1.2);
        } else if (key == 'c') {
            scaleTime(1.2);
        } else if (key == 'x') {
            this.followMouse = !this.followMouse;
        }
    }
}
