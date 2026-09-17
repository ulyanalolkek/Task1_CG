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
    public Landscape(int x, int y, int t, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.t = t;
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

        drawSun(gr, tick);
        drawClouds(t, g);

        drawEarth(300, height, width, (int)(height * 0.55), g , new Color(0, 80, 0));
        drawEarth(-200, height, width, (int)(height * 0.5), g , new Color(0, 100, 0));
        drawEarth(0, height, width, (int)(height * 0.3), g, new Color(70, 134, 70));

        drawTrees(g);

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
    private void drawSun(Graphics gr, int tick) {
        this.tick++;
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.yellow);
        g.fillOval(35, 35, 60, 60);
        AffineTransform old = g.getTransform();
        g.rotate(Math.toRadians(tick), 65, 65);
        g.drawLine(65, 31, 65, 10);
        g.drawLine(82, 36, 94, 22);
        g.drawLine(94, 48, 108, 42);
        g.drawLine(99, 65, 120, 65);
        g.drawLine(94, 82, 106, 94);
        g.drawLine(82, 94, 90, 112);
        g.drawLine(65, 99, 65, 118);
        g.drawLine(48, 94, 38, 104);
        g.drawLine(36, 82, 20, 92);
        g.drawLine(31, 65, 12, 65);
        g.drawLine(36, 48, 24, 36);
        g.drawLine(48, 36, 42, 18);
        g.setTransform(old);


    }
    private void drawClouds(int t, Graphics gr) {
        Random random = new Random();
        if (listCloud.isEmpty()) {
            listCloud.add(new Cloud(100, 50, 40, 5));
            listCloud.add(new Cloud(300, 60, 20, 4));
            listCloud.add(new Cloud(700, 25, 60, 3));
        }
        if (listCloud.size() <= 4) {
            listCloud.add(new Cloud(random.nextInt(101) - 150, random.nextInt(100) + 20, random.nextInt(70) + 50, random.nextInt(3) + 3));
        }
        List<Cloud> toRemove = new ArrayList<>();
        for (Cloud cl : listCloud) {
            cl.setX(cl.getX() + cl.getSpeed());
            if (cl.getX() >= 1000) {
                toRemove.add(cl);
                continue;
            }
            drawCloud(cl.getX(), cl.getY(), gr, cl.getSize());
        }
        for (Cloud cl : toRemove) {
            listCloud.remove(cl);
        }
    }
    private void drawCloud(int x, int y, Graphics gr, int size) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.WHITE);
        g.fillOval(x, y - (int)(size * 0.15), (int)(size * 0.8), (int)(size * 0.5));
        g.fillOval(x + (int)(size * 0.3), y + (int)(size * 0.15), (int)(size * 0.7), (int)(size * 0.4));
        g.fillOval(x + (int)(size * 0.4), y - (int)(size * 0.2), (int)(size * 0.9), (int)(size * 0.5));
    }
    private void drawTrees(Graphics gr) {
        drawOrdinaryTree(750, 460, 300, gr,new Color(0, 40, 0), new Color(110, 63, 0));
        drawChristmasTree(600, 495, 400, gr, new Color(0, 35, 0), new Color(100, 53, 0));
        drawChristmasTree(910, 480, 350, gr, new Color(0, 30, 0), new Color(90, 50, 0));

        drawOrdinaryTree(800, 500, 200, gr,new Color(0, 60, 0), new Color(110, 63, 0));
        drawChristmasTree(900, 540, 200, gr, new Color(0, 55, 0), new Color(100, 63, 0));
        drawChristmasTree(700, 580, 260, gr, new Color(0, 67, 0), new Color(100, 63, 0));


        drawChristmasTree(80, 540, 320, gr, new Color(0, 55, 0), new Color(100, 53, 0));
        drawChristmasTree(250, 500, 270, gr, new Color(0, 40, 0), new Color(90, 50, 0));
        drawOrdinaryTree(500, 540, 300, gr,new Color(0, 60, 0), new Color(110, 63, 0));
        drawOrdinaryTree(830, 640, 200, gr, new Color(0, 70, 0), new Color(100, 63, 0));

        drawChristmasTree(-10, 600, 450, gr, new Color(0, 40, 0), new Color(90, 60, 0));

        drawOrdinaryTree(80, 650, 300, gr,new Color(0, 60, 0), new Color(110, 63, 0));


        drawChristmasTree(280, 650, 720, gr, new Color(0, 70, 0), new Color(110, 73, 0));

    }
    private void drawOrdinaryTree(int x, int y, int size, Graphics gr, Color green,  Color brown) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(brown);
        g.fillRect(x + (int)(size * 0.05), y - (int)(size * 0.8), (int)(size * 0.05), (int)(size * 0.8));
        g.setColor(green);
        g.fillOval(x - (int)(size * 0.15), y - size, (int)(size * 0.3), (int)(size * 0.3));
        g.fillOval(x - (int)(size * 0.3), y - (int)(size * 0.8), (int)(size * 0.45), (int)(size * 0.45));
        g.fillOval(x + (int)(size * 0.05), y - (int)(size * 0.85), (int)(size * 0.4), (int)(size * 0.4));
    }
    private void drawChristmasTree(int x, int y, int size, Graphics gr, Color green,  Color brown) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(brown);
        g.fillRect(x, y - (int)(size * 0.1), (int)(size * 0.1), (int)(size * 0.1));
        g.setColor(green);
        g.fillPolygon(new int[]{x - (int)(size * 0.3), x + (int)(size * 0.4), x + (int)(size * 0.05)}, new int[]{y - (int)(size * 0.1), y - (int)(size * 0.1), y - (int)(size * 0.4)}, 3);
        g.fillPolygon(new int[]{x - (int)(size * 0.2), x + (int)(size * 0.3), x + (int)(size * 0.05)}, new int[]{y - (int)(size * 0.4), y - (int)(size * 0.4), y - (int)(size * 0.65)}, 3);
        g.fillPolygon(new int[]{x - (int)(size * 0.1), x + (int)(size * 0.2), x + (int)(size * 0.05)}, new int[]{y - (int)(size * 0.65), y - (int)(size * 0.65), y - (int)(size * 0.8)}, 3);
    }

}
