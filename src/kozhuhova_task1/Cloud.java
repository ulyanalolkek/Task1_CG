package kozhuhova_task1;

import java.awt.*;

public class Cloud {
    private int x;
    private int y;
    private int size;
    private int speed;
    public Cloud(int x, int y, int size, int speed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getSize() {
        return size;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(255, 255, 255, 218));
        g.fillOval(x, y - (int)(size * 0.15), (int)(size * 0.8), (int)(size * 0.5));
        g.fillOval(x + (int)(size * 0.3), y + (int)(size * 0.15), (int)(size * 0.7), (int)(size * 0.4));
        g.fillOval(x + (int)(size * 0.4), y - (int)(size * 0.2), (int)(size * 0.9), (int)(size * 0.5));
    }
}
