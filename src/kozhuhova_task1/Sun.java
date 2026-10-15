package kozhuhova_task1;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Sun{
    private int x;
    private int y;
    private int tick;
    public Sun(int x, int y, int tick) {
        this.x = x;
        this.y = y;
        this.tick = tick;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getTick() {
        return tick;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setTick(int tick) {
        this.tick = tick;
    }

    public void draw(Graphics2D g) {
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
}
