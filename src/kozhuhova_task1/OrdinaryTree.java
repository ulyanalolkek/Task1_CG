package kozhuhova_task1;

import java.awt.*;

public class OrdinaryTree {
    private int x;
    private int y;
    private int size;
    private Color greenColor;
    private Color brownColor;
    public OrdinaryTree(int x, int y, int size, Color greenColor, Color brownColor) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.greenColor = greenColor;
        this.brownColor = brownColor;
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
    public void draw(Graphics2D g) {
        g.setColor(brownColor);
        g.fillRect(x + (int)(size * 0.05), y - (int)(size * 0.8), (int)(size * 0.05), (int)(size * 0.8));
        g.setColor(greenColor);
        g.fillOval(x - (int)(size * 0.15), y - size, (int)(size * 0.3), (int)(size * 0.3));
        g.fillOval(x - (int)(size * 0.3), y - (int)(size * 0.8), (int)(size * 0.45), (int)(size * 0.45));
        g.fillOval(x + (int)(size * 0.05), y - (int)(size * 0.85), (int)(size * 0.4), (int)(size * 0.4));
    }
}
