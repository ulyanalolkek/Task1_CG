package kozhuhova_task1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawPanel extends JPanel implements ActionListener {
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final int TIMER_DELAY;
    private Timer timer;
    private int tickCloud = 0;
    private int ticksFromStart = 100;
    private int maxTicks = 100;
    private boolean isOwlJump = true;
    private boolean isJumpRopeInFront = true;

    private Owl owl;
    private Landscape landscape;

    public DrawPanel(final int width, final int height, final int timerDelay) {
        this.PANEL_WIDTH = width;
        this.PANEL_HEIGHT = height;
        this.TIMER_DELAY = timerDelay;
        timer = new Timer(timerDelay, this);
        timer.start();

        this.owl = new Owl(200, 200, 400, 500, new Color(128, 0, 128), new Color(160, 32, 160), new Color(100, 0, 100), 0, 0, isOwlJump);
        this.landscape = new Landscape(0, 0, ticksFromStart, 1000, 750);
    }

    @Override
    public void paint(final Graphics gr) {
        super.paint(gr);
        landscape.setT(tickCloud);
        landscape.draw(gr);

        owl.setY(200 + ticksFromStart);
        owl.setAngle(ticksFromStart / 3 + 14);
        owl.setTick(100 - ticksFromStart);
        owl.setOwlJump(isOwlJump);
        owl.draw(gr);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer) {
            repaint();
            tickCloud++;
            if (isOwlJump) {
                ticksFromStart -= 5;
                if (ticksFromStart == 0) {
                    isOwlJump = false;
                }
            }
            if (!isOwlJump) {
                ticksFromStart += 5;
                if (maxTicks == ticksFromStart) {
                    isOwlJump = true;
                }
            }
        }
    }
}
