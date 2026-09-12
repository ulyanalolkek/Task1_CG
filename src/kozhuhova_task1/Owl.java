package kozhuhova_task1;

import java.awt.*;

public class Owl {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color colorMain;
    private Color colorLight;
    private Color colorDark;
    private int t;

    public Owl(int x, int y, int width, int height, Color colorMain, Color colorLight, Color colorDark, int t) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colorMain = colorMain;
        this.colorLight = colorLight;
        this.colorDark = colorDark;

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

    public void setColorMain(Color colorMain) {
        this.colorMain = colorMain;
    }
    public void setT(int t) {
        this.t = t;
    }
    void draw(Graphics gr) {
        this.y += this.t;
        Graphics2D g = (Graphics2D) gr;
        // ноги
        // по н +t и пусть разъезжаются
        g.setColor(this.colorMain);
        g.fillOval(this.x + width / 3 + width / 8, this.y + (int)(height * 0.55), (int)(width * 0.1), (int)(height * 0.15));
        g.setColor(Color.BLACK);
        g.drawOval(this.x + width / 3 + width / 8, this.y + (int)(height * 0.55), (int)(width * 0.1), (int)(height * 0.15));

        g.setColor(this.colorMain);
        g.fillOval(this.x + (int)(width * 0.62), this.y + (int)(height * 0.55), (int)(width * 0.1), (int)(height * 0.15));
        g.setColor(Color.BLACK);
        g.drawOval(this.x + (int)(width * 0.62), this.y + (int)(height * 0.55), (int)(width * 0.1), (int)(height * 0.15));
        // тело
        g.setColor(this.colorMain);
        g.fillOval(this.x + width / 3, this.y + height / 6, width / 2, height / 2);
        g.setColor(Color.BLACK);
        g.drawOval(this.x + width / 3 , this.y + height / 6, width / 2, height / 2);

        g.setColor(this.colorLight);
        g.fillOval(this.x + width / 3 + width / 12, this.y + height / 6, width / 3,(int)(height * 0.4) );
        g.setColor(Color.BLACK);
        g.drawOval(this.x + width / 3 + width / 12, this.y + height / 6, width / 3, (int)(height * 0.4));

        //голова
        g.setColor(this.colorMain);
        g.fillOval(this.x + width / 3, this.y, width / 2, height / 3);
        g.setColor(Color.BLACK);
        g.drawOval(this.x + width / 3, this.y , width / 2, height / 3);

        g.setColor(this.colorLight);
        g.fillOval(this.x + width / 3 + (int)(width * 0.05), this.y + (int)(height * 0.05), width / 2 - (int)(width * 0.1), height / 3 -(int)(height * 0.05));
        g.setColor(Color.BLACK);
        g.drawOval(this.x + width / 3 + (int)(width * 0.05), this.y + (int)(height * 0.05), width / 2 - (int)(width * 0.1), height / 3 - (int)(height * 0.05));

        //глаза
        g.setColor(Color.WHITE);
        g.fillOval(this.x + width / 3 + (int)(width * 0.1), this.y + (int)(height * 0.1), (int)(width * 0.15), (int)(width * 0.15));
        g.setColor(Color.BLACK);
        g.drawOval(this.x + width / 3 + (int)(width * 0.1), this.y + (int)(height * 0.1), (int)(width * 0.15), (int)(width * 0.15));

        g.setColor(Color.WHITE);
        g.fillOval(this.x + width / 3 + width / 4 , this.y + (int)(height * 0.1), (int)(width * 0.15), (int)(width * 0.15));
        g.setColor(Color.BLACK);
        g.drawOval(this.x + + width / 3 + width / 4, this.y + (int)(height * 0.1), (int)(width * 0.15), (int)(width * 0.15));
        //зрачок
        g.fillOval(this.x + width / 3 + (int)(width * 0.1), this.y + (int)(height * 0.1), (int)(width * 0.13), (int)(width * 0.13));
        g.fillOval(this.x + width / 3 + width / 4 , this.y + (int)(height * 0.1), (int)(width * 0.13), (int)(width * 0.13));
        // блики
        g.setColor(Color.WHITE);
        g.fillOval(this.x + width / 3 + (int)(width * 0.12), this.y + (int)(height * 0.11), (int)(width * 0.04), (int)(width * 0.04));
        g.fillOval(this.x + width / 3 + width / 4 + (int)(width * 0.02) , this.y + (int)(height * 0.11), (int)(width * 0.04), (int)(width * 0.04));
        //клюв
        g.setColor(new Color(240, 160, 60));
        int[] x = {this.x + width * 7 / 12 - (int)(width * 0.05), this.x + width * 7 / 12 + (int)(width * 0.05), this.x + width * 7 / 12};
        int[] y = {this.y + (int)(height * 0.1) + (int)(width * 0.15), this.y + (int)(height * 0.1) + (int)(width * 0.15), this.y + (int)(height * 0.12) + (int)(width * 0.15)};
        g.fillPolygon(x, y, 3);
        g.drawPolygon(x, y, 3);

        //когти
        g.fillOval(this.x + width / 3 + width / 8, this.y + (int)(height * 0.67), (int)(width * 0.03), (int)(height * 0.035));
        g.fillOval(this.x + width / 3 + width / 8 + (int)(width * 0.03), this.y + (int)(height * 0.67), (int)(width * 0.03), (int)(height * 0.035));
        g.fillOval(this.x + width / 3 + width / 8 + (int)(width * 0.03) * 2, this.y + (int)(height * 0.67), (int)(width * 0.03), (int)(height * 0.035));

        g.fillOval(this.x + (int)(width * 0.62), this.y + (int)(height * 0.67), (int)(width * 0.03), (int)(height * 0.035));
        g.fillOval(this.x + (int)(width * 0.62) + (int)(width * 0.03), this.y + (int)(height * 0.67), (int)(width * 0.03), (int)(height * 0.035));
        g.fillOval(this.x + (int)(width * 0.62) + (int)(width * 0.03) * 2, this.y + (int)(height * 0.67), (int)(width * 0.03), (int)(height * 0.035));

        //брови
        g.setColor(colorDark);




    }
}
