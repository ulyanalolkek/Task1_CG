package kozhuhova_task1;

import java.awt.*;

public class ChristmasTree {
    private int x;
    private int y;
    private int size;
    private Color greenColor;
    private Color brownColor;
    public ChristmasTree(int x, int y, int size, Color greenColor, Color brownColor) {
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
        g.fillRect(x, y - (int)(size * 0.1), (int)(size * 0.1), (int)(size * 0.1));
        g.setColor(greenColor);
        g.fillPolygon(new int[]{x - (int)(size * 0.3), x + (int)(size * 0.4), x + (int)(size * 0.05)}, new int[]{y - (int)(size * 0.1), y - (int)(size * 0.1), y - (int)(size * 0.4)}, 3);
        g.fillPolygon(new int[]{x - (int)(size * 0.2), x + (int)(size * 0.3), x + (int)(size * 0.05)}, new int[]{y - (int)(size * 0.35), y - (int)(size * 0.35), y - (int)(size * 0.6)}, 3);
        g.fillPolygon(new int[]{x - (int)(size * 0.1), x + (int)(size * 0.2), x + (int)(size * 0.05)}, new int[]{y - (int)(size * 0.55), y - (int)(size * 0.55), y - (int)(size * 0.7)}, 3);
        g.setColor(new Color(53, 36, 24, 253));
        g.drawLine(x + (int)(size * 0.01), y - (int)(size * 0.09), x + (int)(size * 0.01), y - (int)(size * 0.02));
        g.drawLine(x + (int)(size * 0.02), y - (int)(size * 0.1), x + (int)(size * 0.02), y - (int)(size * 0.07));
        g.drawLine(x + (int)(size * 0.02), y - (int)(size * 0.06), x + (int)(size * 0.02), y - (int)(size * 0.01));
        g.drawLine(x + (int)(size * 0.035), y - (int)(size * 0.09), x + (int)(size * 0.035), y - (int)(size * 0.05));
        g.drawLine(x + (int)(size * 0.035), y - (int)(size * 0.04), x + (int)(size * 0.035), y - (int)(size * 0.01));
        g.drawLine(x + (int)(size * 0.05), y - (int)(size * 0.09), x + (int)(size * 0.05), y - (int)(size * 0.03));
        g.drawLine(x + (int)(size * 0.06), y - (int)(size * 0.10), x + (int)(size * 0.06), y - (int)(size * 0.07));
        g.drawLine(x + (int)(size * 0.06), y - (int)(size * 0.06), x + (int)(size * 0.06), y - (int)(size * 0.05));
        g.drawLine(x + (int)(size * 0.06), y - (int)(size * 0.04), x + (int)(size * 0.06), y - (int)(size * 0.01));
        g.drawLine(x + (int)(size * 0.075), y - (int)(size * 0.09), x + (int)(size * 0.075), y - (int)(size * 0.05));
        g.drawLine(x + (int)(size * 0.075), y - (int)(size * 0.04), x + (int)(size * 0.075), y - (int)(size * 0.01));
        g.drawLine(x + (int)(size * 0.09), y - (int)(size * 0.09), x + (int)(size * 0.09), y - (int)(size * 0.03));
        g.drawLine(x + (int)(size * 0.09), y - (int)(size * 0.023), x + (int)(size * 0.09), y - (int)(size * 0.01));

    }

}
