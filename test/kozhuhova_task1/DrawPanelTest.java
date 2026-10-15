package kozhuhova_task1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DrawPanelTest {

    private static final int PANEL_WIDTH = 1000;
    private static final int PANEL_HEIGHT = 750;
    private static final int LONG_DELAY_MS = 100_000;

    private static final int OWL_BASE_Y = 200;
    private static final int START_TICKS = 100;
    private static final int JUMP_STEP = 5;

    private DrawPanel panel;
    private BufferedImage image;
    private Graphics2D g;

    @BeforeEach
    void setUp() throws InvocationTargetException, InterruptedException {
        // Swing-компоненты должны создаваться в Event Dispatch Thread
        SwingUtilities.invokeAndWait(() ->
                panel = new DrawPanel(PANEL_WIDTH, PANEL_HEIGHT, LONG_DELAY_MS));
        image = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
    }

    @AfterEach
    void tearDown() {
        panel.getTimer().stop();
        g.dispose();
    }

    // ---------- создание ----------

    @Test
    @DisplayName("Панель создаётся и содержит сову и ландшафт")
    void panelCreatesOwlAndLandscape() {
        assertNotNull(panel);
        assertNotNull(panel.getOwl(), "Сова должна быть создана в конструкторе");
        assertNotNull(panel.getLandscape(), "Ландшафт должен быть создан в конструкторе");
    }

    @Test
    @DisplayName("Таймер создаётся, запущен и имеет заданную задержку")
    void timerIsRunningWithGivenDelay() {
        final Timer timer = panel.getTimer();

        assertNotNull(timer);
        assertTrue(timer.isRunning(), "Таймер должен быть запущен");
        assertEquals(LONG_DELAY_MS, timer.getDelay());
    }

    @Test
    @DisplayName("Сова создаётся с ожидаемыми размерами и позицией")
    void owlHasExpectedGeometry() {
        final Owl owl = panel.getOwl();

        assertEquals(200, owl.getX());
        assertEquals(200, owl.getY());
        assertEquals(400, owl.getWidth());
        assertEquals(500, owl.getHeight());
    }

    @Test
    @DisplayName("Ландшафт создаётся с размерами 1000x750")
    void landscapeHasExpectedSize() {
        assertEquals(1000, panel.getLandscape().getWidth());
        assertEquals(750, panel.getLandscape().getHeight());
    }

    // ---------- первая отрисовка ----------

    @Test
    @DisplayName("Первая отрисовка: сова внизу, прыжок вверх, крылья по формуле")
    void firstPaintSetsInitialOwlState() {
        panel.paint(g);

        final Owl owl = panel.getOwl();
        assertEquals(OWL_BASE_Y + START_TICKS, owl.getY());
        assertEquals(START_TICKS / 3 + 14, owl.getAngle());
        assertEquals(0, owl.getTick());
        assertTrue(owl.isOwlJump());
    }

    @Test
    @DisplayName("paint() рисует непрозрачные пиксели")
    void paintProducesOpaquePixels() {
        panel.paint(g);

        assertEquals(0xFF, image.getRGB(500, 745) >>> 24);
        assertEquals(0xFF, image.getRGB(500, 5) >>> 24);
    }

    @Test
    @DisplayName("paint() не выбрасывает исключений при многократном вызове")
    void repeatedPaintDoesNotThrow() {
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 50; i++) {
                panel.paint(g);
            }
        });
    }

    // ---------- actionPerformed ----------

    @Test
    @DisplayName("Первый тик поднимает сову на 5 пикселей")
    void firstTickRaisesOwlByStep() {
        tick();
        panel.paint(g);

        final Owl owl = panel.getOwl();
        assertEquals(OWL_BASE_Y + START_TICKS - JUMP_STEP, owl.getY());
        assertEquals(JUMP_STEP, owl.getTick());
        assertEquals((START_TICKS - JUMP_STEP) / 3 + 14, owl.getAngle());
    }

    @Test
    @DisplayName("Событие от чужого источника игнорируется")
    void foreignEventIgnored() {
        panel.actionPerformed(new ActionEvent(new Object(),
                ActionEvent.ACTION_PERFORMED, "tick"));
        panel.paint(g);

        assertEquals(OWL_BASE_Y + START_TICKS, panel.getOwl().getY(),
                "Событие от чужого источника не должно двигать сову");
    }

    @Test
    @DisplayName("Многократные вызовы actionPerformed не падают")
    void repeatedTicksDoNotThrow() {
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 1000; i++) {
                tick();
            }
        });
    }

    @Test
    @DisplayName("Высота совы остаётся в пределах [205, 300]")
    void owlHeightStaysInRange() {
        for (int i = 0; i < 300; i++) {
            tick();
            panel.paint(g);

            final int y = panel.getOwl().getY();
            assertTrue(y >= OWL_BASE_Y + JUMP_STEP && y <= OWL_BASE_Y + START_TICKS,
                    "Шаг " + i + ": y = " + y + " вне диапазона [205, 300]");
        }
    }

    @Test
    @DisplayName("За один тик сова смещается не больше чем на 5 пикселей")
    void owlMovesAtMostOneStepPerTick() {
        panel.paint(g);
        int previous = panel.getOwl().getY();

        for (int i = 0; i < 300; i++) {
            tick();
            panel.paint(g);

            final int current = panel.getOwl().getY();
            assertTrue(Math.abs(current - previous) <= JUMP_STEP,
                    "Шаг " + i + ": скачок с " + previous + " до " + current);
            previous = current;
        }
    }

    @Test
    @DisplayName("Сова достигает и верхней, и нижней точек прыжка")
    void owlReachesTopAndBottom() {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int i = 0; i < 200; i++) {
            tick();
            panel.paint(g);
            minY = Math.min(minY, panel.getOwl().getY());
            maxY = Math.max(maxY, panel.getOwl().getY());
        }

        assertTrue(minY <= OWL_BASE_Y + JUMP_STEP, "Сова должна подниматься вверх, minY = " + minY);
        assertEquals(OWL_BASE_Y + START_TICKS, maxY, "Сова должна возвращаться вниз");
    }

    @Test
    @DisplayName("Движение совы периодично")
    void owlMotionIsPeriodic() {
        final int[] ys = new int[400];
        for (int i = 0; i < ys.length; i++) {
            tick();
            panel.paint(g);
            ys[i] = panel.getOwl().getY();
        }

        int period = -1;
        for (int p = 1; p <= 100 && period < 0; p++) {
            boolean ok = true;
            for (int i = 0; i + p < ys.length; i++) {
                if (ys[i] != ys[i + p]) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                period = p;
            }
        }

        assertTrue(period > 0, "Движение совы должно повторяться с некоторым периодом");
    }

    @Test
    @DisplayName("Режим прыжка меняется: сначала true, потом false, потом снова true")
    void jumpFlagToggles() {
        boolean sawJump = false;
        boolean sawNoJump = false;
        boolean returnedToJump = false;

        for (int i = 0; i < 200; i++) {
            tick();
            panel.paint(g);

            final boolean jump = panel.getOwl().isOwlJump();
            if (jump && !sawNoJump) {
                sawJump = true;
            }
            if (!jump) {
                sawNoJump = true;
            }
            if (jump && sawNoJump) {
                returnedToJump = true;
            }
        }

        assertTrue(sawJump, "Сначала сова должна быть в режиме прыжка");
        assertTrue(sawNoJump, "Режим прыжка должен выключаться");
        assertTrue(returnedToJump, "Режим прыжка должен включаться снова");
    }

    @Test
    @DisplayName("Угол крыльев и tick согласованы с высотой совы")
    void angleAndTickAreConsistentWithHeight() {
        for (int i = 0; i < 100; i++) {
            tick();
            panel.paint(g);

            final Owl owl = panel.getOwl();
            final int ticksFromStart = owl.getY() - OWL_BASE_Y;
            assertEquals(ticksFromStart / 3 + 14, owl.getAngle(),
                    "Шаг " + i + ": угол должен считаться по формуле ticks / 3 + 14");
            assertEquals(START_TICKS - ticksFromStart, owl.getTick(),
                    "Шаг " + i + ": tick должен быть равен 100 - ticks");
        }
    }

    @Test
    @DisplayName("Облака двигаются при каждой отрисовке")
    void cloudsMoveWithPaint() {
        final Cloud first = panel.getLandscape().getClouds().get(0);
        final int before = first.getX();

        panel.paint(g);

        assertEquals(before + first.getSpeed(), first.getX(),
                "Облако должно сместиться на свою скорость");
    }

    // ---------- вспомогательные методы ----------

    private void tick() {
        panel.actionPerformed(new ActionEvent(panel.getTimer(),
                ActionEvent.ACTION_PERFORMED, "tick"));
    }
}
