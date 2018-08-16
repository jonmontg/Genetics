import processing.core.*;

import java.util.Random;

class Simulation {

    private Sketch p;

    private Population[] populations;
    private int simFrames;
    private int currFrame = 0;
    private double speedMult = 1;
    private PVector target;
    private boolean sameTarget = true;
    private int generation;

    Simulation(Sketch p, int length, Population[] creatures) {
        this.p = p;
        this.target = new PVector(this.p.width / 2, 50);
        this.simFrames = length;
        this.populations = creatures;
        this.generation = 1;
    }

    void update() {
        for (int frame = 0; frame < (int)this.speedMult; frame++) {
            this.currFrame++;
            for (Population p: this.populations) {
                p.update();
            }
            if (this.currFrame >= simFrames) {
                nextGen();
                break;
            }
        }
    }

    private void nextGen() {
        this.currFrame = 0;
        this.generation++;
        for (Population p: this.populations) {
            System.out.println(p.avgFitness());
            p.nextGen();
        }
        setTargets();
    }

    void draw() {
        p.background(200);
        p.textSize(20);
        p.text(this.generation, 40, 40);
        for (Population p: this.populations) {
            p.draw();
        }
        p.fill(255);
        p.ellipse((int)this.target.x, (int)this.target.y, 20, 20);
    }

    private void setTargets() {

        Random r = new Random();
        float x = r.nextInt(this.p.width);
        float y = r.nextInt(this.p.height);
        this.target = new PVector(x, y);

        for (Population p: this.populations) {
            for (Creature c: p.getCreatures()) {
                if (!this.sameTarget) {
                    x = r.nextInt(this.p.width);
                    y = r.nextInt(this.p.height);
                }
                c.setTarget(x, y);
            }
        }
    }

    private void scaleTime (double scale) {
        this.speedMult = Math.min(Math.max(1, (int)(speedMult * scale)), this.simFrames);
    }

    private void scaleLength (double scale) {
        this.simFrames = Math.min(Math.max(100, (int)(this.simFrames * scale)), 10000);
    }

    void keyPressed(char key) {
        switch(key) {
            case 'z':
                scaleTime(.5);
                break;
            case 'c':
                scaleTime(2);
                break;
            case 'a':
                scaleLength(.5);
                break;
            case 'd':
                scaleLength(2);
                break;
            case 's':
                this.sameTarget = !this.sameTarget;
                break;
        }
    }
}
