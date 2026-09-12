package kozhuhova_task1;

import java.awt.*;

public class Owl {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int t;

    public Owl(int x, int y, int width, int height, Color color, int t) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.t = t;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public void setT(int t) {
        this.t = t;
    }
    void draw(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        // ноги
        g.setColor(this.color);
        g.fillOval(this.x + (int)(width / 3), this.y + (int)(height * 9 / 10), (int)(width * 0.05), (int)(height * 0.1));
        g.setColor(Color.BLACK);
        g.drawOval(this.x + (int)(width / 3), this.y + (int)(height * 9 / 10), (int)(width * 0.05), (int)(height * 0.1));

        g.setColor(this.color);
        g.fillOval(this.x + (int)(width * 2 / 3), this.y + (int)(height * 9 / 10), (int)(width * 0.05), (int)(height * 0.1));
        g.setColor(Color.BLACK);
        g.drawOval(this.x + (int)(width * 2 / 3), this.y + (int)(height * 9 / 10), (int)(width * 0.05), (int)(height * 0.1));
        // тело


    }
}
