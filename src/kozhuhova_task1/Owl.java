package kozhuhova_task1;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;

public class Owl {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color colorMain;
    private Color colorLight;
    private Color colorDark;
    private int angle;
    private int tick;
    private boolean isOwlJump;


    public Owl(int x, int y, int width, int height, Color colorMain, Color colorLight, Color colorDark, int angle, int tick, boolean isOwlJump) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colorMain = colorMain;
        this.colorLight = colorLight;
        this.colorDark = colorDark;

        this.angle = angle;
        this.tick = tick;
        this.isOwlJump = isOwlJump;
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
    public void setAngle(int angle) {
        this.angle = angle;
    }
    public void setTick(int tick) {
        this.tick = tick;
    }
    public void setOwlJump(boolean isOwlJump) {
        this.isOwlJump = isOwlJump;
    }
    void draw(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        // ноги
        // по н +t и пусть разъезжаются
        if (!isOwlJump) {
            // скакалка
            g.setColor(new Color(210, 105, 30));
            makeRollingPin(
                    (int)(this.x + (int)(width * 0.03) + width / 4.0
                            - ((int)(height * 0.65) - height / 2.0) * Math.sin(Math.toRadians(angle))),
                    (int)(this.y + height / 2.0
                            + ((int)(height * 0.65) - height / 2.0) * Math.cos(Math.toRadians(angle))),

                    (int)(this.x + (int)(width * 0.63) + width / 4.0
                            + ((int)(height * 0.65) - height / 2.0) * Math.sin(Math.toRadians(angle))),
                    (int)(this.y + height / 2.0
                            + ((int)(height * 0.65) - height / 2.0) * Math.cos(Math.toRadians(angle))),
                    tick * 0.01, g, width, (int)(height * 1.3) , this.x ,this.y - (int)(height * 0.4));


        }
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
        makeBrows(this.x, this.y, 30, g, width, height);
        g.setColor(colorDark);
        makeBrows(this.x + (int)(width * 0.17), this.y - (int)(height * 0.01), -28, g, width, height);

        //крылья
        g.setColor(colorMain);
        makeWing(this.x + (int)(width * 0.03), this.y, angle, g, width, height);
        makeWing(this.x + (int)(width * 0.63), this.y, -angle, g, width, height);
        if (isOwlJump) {
            // скакалка
            g.setColor(new Color(210, 105, 30));
            makeRollingPin(
                    (int) (this.x + (int) (width * 0.03) + width / 4.0
                            - ((int) (height * 0.65) - height / 2.0) * Math.sin(Math.toRadians(angle))),
                    (int) (this.y + height / 2.0
                            + ((int) (height * 0.65) - height / 2.0) * Math.cos(Math.toRadians(angle))),

                    (int) (this.x + (int) (width * 0.63) + width / 4.0
                            + ((int) (height * 0.65) - height / 2.0) * Math.sin(Math.toRadians(angle))),
                    (int) (this.y + height / 2.0
                            + ((int) (height * 0.65) - height / 2.0) * Math.cos(Math.toRadians(angle))),
                    tick * 0.01, g, width, (int) (height * 1.3), this.x, this.y - (int) (height * 0.4));
        }
    }
    private static void makeRollingPin(int x1, int y1, int x2, int y2, double t, Graphics2D g, int width, int height, int x, int y) {
        CubicCurve2D rp = new CubicCurve2D.Double();
        rp.setCurve(
                x1, y1,
                x1 + (int)(width * 0.1), y + (int)(height * t),
                x2 - (int)(width * 0.1), y + (int)(height * t),
                x2, y2
        );
        g.draw(rp);

    }
    private static void makeBrows(int x, int y, int a, Graphics2D g, int width, int height) {
        AffineTransform old = g.getTransform();
        g.rotate(Math.toRadians(a), x + width * 0.5, y + height * 0.075);
        g.fillOval(x + width / 3 + (int)(width * 0.1), y + (int)(height * 0.05), (int)(width * 0.1), (int)(height * 0.05));
        g.setColor(Color.black);
        g.drawOval(x + width / 3 + (int)(width * 0.1), y + (int)(height * 0.05), (int)(width * 0.1), (int)(height * 0.05));
        g.setTransform(old);

    }
    private static void makeWing(int x, int y, int a, Graphics2D g, int width, int height) {
        Path2D wing1 = new Path2D.Double();

        wing1.moveTo(x + width / 4, y + height / 3);

        wing1.curveTo(
                x + width / 7, y + (int)(height * 0.45),
                x + width / 7, y + (int)(height * 0.55),
                x + width / 4, y + (int)(height * 0.65)
        );

        wing1.curveTo(
                x + width / 2.8, y + (int)(height * 0.55),
                x + width / 2.8, y + (int)(height * 0.45),
                x + width / 4, y + height / 3
        );

        wing1.closePath();
        AffineTransform old = g.getTransform();
        g.rotate(Math.toRadians(a), x + width/4, y + height/2);
        g.fill(wing1);
        g.draw(wing1);
        g.setTransform(old);

    }
}
