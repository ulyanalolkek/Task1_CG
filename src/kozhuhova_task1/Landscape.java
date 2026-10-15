package kozhuhova_task1;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Landscape {
    private int x;
    private int y;
    private int width;
    private int height;
    private int t;
    private int tick = 0;
    private List<Cloud> listCloud = new ArrayList<Cloud>();
    private List<ChristmasTree> listCT = new ArrayList<>();
    private List<OrdinaryTree> listOT = new ArrayList<>();
    private Sun s;
    public Landscape(int x, int y, int t, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.t = t;

        s = new Sun(0, 0, 0);
        generateClouds();

    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public List<Cloud> getClouds() {
        return listCloud;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setT(int t) {
        this.t = t;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void draw(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(new Color(150, 240, 255));
        g.fillRect(0, 0, width, height);

        s.draw(g);
        s.setTick(s.getTick()+1);
        generateClouds();
        for(Cloud cl : listCloud) {
            cl.draw(g);
        }
        g.setColor(new Color(3, 60, 3));
        g.fillRect(0, 450, 1000, 300);
        drawEarth(300, height, width, (int)(height * 0.55), g , new Color(0, 80, 0));
        drawEarth(-200, height, width, (int)(height * 0.5), g , new Color(10, 100, 10));
        drawEarth(0, height, width, (int)(height * 0.3), g, new Color(74, 122, 61));

        generateTrees(g);

    }
    private void drawEarth(int x, int y, int width, int height, Graphics gr, Color color) {
        Graphics2D g = (Graphics2D) gr;
        Path2D earth = new Path2D.Double();
        earth.moveTo(x, y);
        earth.curveTo(x + (int)(width * 0.3), y - height,
                x + (int)(width * 0.7), y - height,
                x + width, y);
        earth.closePath();
        g.setColor(color);
        g.fill(earth);
        g.draw(earth);
    }

    private void generateClouds() {
        Random random = new Random();
        if (listCloud.isEmpty()) {
            listCloud.add(new Cloud(100, 50, 40, 5));
            listCloud.add(new Cloud(300, 60, 20, 4));
            listCloud.add(new Cloud(700, 25, 60, 3));
        }
        if (listCloud.size() <= 5) {
            listCloud.add(new Cloud(random.nextInt(101) - 150, random.nextInt(100) + 20, random.nextInt(70) + 50, random.nextInt(3) + 3));
        }
        List<Cloud> toRemove = new ArrayList<>();
        for (Cloud cl : listCloud) {
            cl.setX(cl.getX() + cl.getSpeed());
            if (cl.getX() >= 1000) {
                toRemove.add(cl);
                continue;
            }
        }
        for (Cloud cl : toRemove) {
            listCloud.remove(cl);
        }
    }
    private void generateTrees(Graphics2D gr) {
        new OrdinaryTree(750, 460, 300, new Color(0, 40, 0), new Color(110, 63, 0)).draw(gr);
        new ChristmasTree(600, 495, 400, new Color(0, 35, 0), new Color(100, 53, 0)).draw(gr);
        new ChristmasTree(910, 480, 350, new Color(0, 30, 0), new Color(90, 50, 0)).draw(gr);

        new OrdinaryTree(800, 500, 200, new Color(0, 60, 0), new Color(110, 63, 0)).draw(gr);
        new ChristmasTree(900, 540, 200, new Color(0, 55, 0), new Color(100, 63, 0)).draw(gr);
        new ChristmasTree(700, 580, 260,  new Color(0, 67, 0), new Color(100, 63, 0)).draw(gr);


        new ChristmasTree(80, 540, 320,  new Color(0, 55, 0), new Color(100, 53, 0)).draw(gr);
        new ChristmasTree(250, 500, 270,  new Color(0, 40, 0), new Color(90, 50, 0)).draw(gr);
        new OrdinaryTree(500, 540, 300, new Color(0, 60, 0), new Color(110, 63, 0)).draw(gr);
        new OrdinaryTree(830, 640, 200,  new Color(0, 70, 0), new Color(100, 63, 0)).draw(gr);

        new ChristmasTree(-10, 600, 450,  new Color(0, 40, 0), new Color(90, 60, 0)).draw(gr);

        new OrdinaryTree(80, 650, 300, new Color(0, 60, 0), new Color(110, 63, 0)).draw(gr);


        new ChristmasTree(280, 650, 720,  new Color(0, 70, 0), new Color(110, 73, 0)).draw(gr);

    }

}
