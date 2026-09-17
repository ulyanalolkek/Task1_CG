package kozhuhova_task1;

import java.awt.*;
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

        drawSun(gr, width, t);
        drawClouds(t, g);

        drawEarth(300, height, width, (int)(height * 0.55), g , new Color(0, 80, 0));
        drawEarth(-200, height, width, (int)(height * 0.5), g , new Color(0, 100, 0));
        drawEarth(0, height, width, (int)(height * 0.3), g, new Color(70, 134, 70));

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
    private void drawSun(Graphics gr, int width, int t) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.yellow);
        g.fillOval(35, 35, (int)(width * 0.09), (int)(width * 0.09));
        //g.rotate(Math.toRadians(t), 50 + (int)(width * 0.05), 50 + (int)(width * 0.05));
        g.drawLine(5, 5, 30, 30);
        g.drawLine(70, 5, 70, 25);

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

}
