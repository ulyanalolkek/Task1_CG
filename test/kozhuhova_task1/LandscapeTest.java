package kozhuhova_task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LandscapeTest {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 750;

    private static final Color SKY_COLOR = new Color(150, 240, 255);
    private static final Color SUN_COLOR = Color.YELLOW;
    private static final Color FRONT_HILL_COLOR = new Color(74, 122, 61);

    private static final int MIN_CLOUD_SPEED = 3;
    private static final int MAX_CLOUD_SPEED = 5;
    private static final int MAX_CLOUDS = 6;

    private Landscape landscape;

    @BeforeEach
    void setUp() {
        landscape = new Landscape(0, 0, 0, WIDTH, HEIGHT);
    }

    // ---------- состояние ----------

    @Test
    @DisplayName("Конструктор сохраняет ширину и высоту")
    void constructorStoresSize() {
        assertEquals(WIDTH, landscape.getWidth());
        assertEquals(HEIGHT, landscape.getHeight());
    }

    @Test
    @DisplayName("setWidth и setHeight сохраняют значения")
    void sizeSettersStoreValues() {
        landscape.setWidth(500);
        landscape.setHeight(300);

        assertEquals(500, landscape.getWidth());
        assertEquals(300, landscape.getHeight());
    }

    @Test
    @DisplayName("Облака создаются уже в конструкторе")
    void cloudsAreCreatedInConstructor() {
        assertTrue(landscape.getClouds().size() >= 3,
                "Минимум 3 начальных облака должны появиться сразу");
    }

    // ---------- отрисовка ----------

    @Test
    @DisplayName("draw() не выбрасывает исключений")
    void drawDoesNotThrow() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> landscape.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("draw() закрашивает всю картинку")
    void drawFillsWholeImage() {
        final BufferedImage image = render(landscape);

        for (int x = 0; x < WIDTH; x += 50) {
            for (int y = 0; y < HEIGHT; y += 50) {
                assertEquals(0xFF, image.getRGB(x, y) >>> 24,
                        "Пиксель (" + x + ", " + y + ") должен быть непрозрачным");
            }
        }
    }

    @Test
    @DisplayName("Небо рисуется голубым цветом")
    void skyIsLightBlue() {
        assertEquals(SKY_COLOR.getRGB(), render(landscape).getRGB(500, 5));
    }

    @Test
    @DisplayName("Солнце рисуется жёлтым цветом")
    void sunIsYellow() {
        assertEquals(SUN_COLOR.getRGB(), render(landscape).getRGB(65, 65));
    }

    @Test
    @DisplayName("Передний холм рисуется светло-зелёным цветом")
    void frontHillColor() {
        assertEquals(FRONT_HILL_COLOR.getRGB(), render(landscape).getRGB(500, 745));
    }

    @Test
    @DisplayName("Земля темнее неба")
    void groundIsDarkerThanSky() {
        final BufferedImage image = render(landscape);

        assertTrue(brightness(image.getRGB(500, 745)) < brightness(image.getRGB(500, 5)));
    }

    @Test
    @DisplayName("Деревья рисуются поверх земли (в картинке есть тёмно-зелёный цвет)")
    void treesAreDrawn() {
        assertTrue(countColor(render(landscape), new Color(0, 70, 0)) > 0,
                "Самая передняя ёлка должна быть видна");
    }

    @Test
    @DisplayName("Размеры неба определяются шириной и высотой ландшафта")
    void skySizeFollowsLandscapeSize() {
        landscape.setWidth(300);
        landscape.setHeight(200);

        assertEquals(SKY_COLOR.getRGB(), render(landscape).getRGB(250, 5));
    }

    @Test
    @DisplayName("draw() восстанавливает трансформацию Graphics2D")
    void drawRestoresTransform() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();
        final AffineTransform before = g.getTransform();

        landscape.draw(g);

        assertEquals(before, g.getTransform(),
                "draw() должен восстанавливать исходную трансформацию");
        g.dispose();
    }

    @Test
    @DisplayName("draw() работает при нулевых размерах")
    void drawWithZeroSize() {
        final Landscape zero = new Landscape(0, 0, 0, 0, 0);
        final BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> zero.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("Многократные вызовы draw() не падают")
    void repeatedDrawDoesNotThrow() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> {
            for (int i = 0; i < 500; i++) {
                landscape.setT(i);
                landscape.draw(g);
            }
        });

        g.dispose();
    }

    // ---------- анимация облаков ----------

    @Test
    @DisplayName("Картинка меняется от кадра к кадру (облака двигаются)")
    void imageChangesBetweenFrames() {
        final BufferedImage first = render(landscape);
        final BufferedImage second = render(landscape);

        assertFalse(sameImage(first, second), "Облака должны двигаться");
    }

    @Test
    @DisplayName("Облака сдвигаются вправо на свою скорость при каждом кадре")
    void cloudsMoveBySpeed() {
        final List<Cloud> clouds = new ArrayList<>(landscape.getClouds());
        final List<Integer> oldX = new ArrayList<>();
        final List<Integer> speeds = new ArrayList<>();
        for (final Cloud cloud : clouds) {
            oldX.add(cloud.getX());
            speeds.add(cloud.getSpeed());
        }

        render(landscape);

        for (int i = 0; i < clouds.size(); i++) {
            assertEquals(oldX.get(i) + speeds.get(i), clouds.get(i).getX(),
                    "Облако " + i + " должно сдвинуться на свою скорость");
        }
    }

    @Test
    @DisplayName("Облаков никогда не больше шести")
    void cloudCountIsLimited() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        for (int i = 0; i < 1000; i++) {
            landscape.draw(g);
            assertTrue(landscape.getClouds().size() <= MAX_CLOUDS,
                    "Слишком много облаков: " + landscape.getClouds().size());
        }

        g.dispose();
    }

    @Test
    @DisplayName("Облака, ушедшие за правый край, удаляются")
    void cloudsOutsideAreRemoved() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        for (int i = 0; i < 1000; i++) {
            landscape.draw(g);
            for (final Cloud cloud : landscape.getClouds()) {
                assertTrue(cloud.getX() < WIDTH,
                        "Облако за правым краем должно быть удалено, x = " + cloud.getX());
            }
        }

        g.dispose();
    }

    @Test
    @DisplayName("Скорость каждого облака лежит в диапазоне [3, 5]")
    void cloudSpeedIsInRange() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        for (int i = 0; i < 300; i++) {
            landscape.draw(g);
            for (final Cloud cloud : landscape.getClouds()) {
                assertTrue(cloud.getSpeed() >= MIN_CLOUD_SPEED
                                && cloud.getSpeed() <= MAX_CLOUD_SPEED,
                        "Скорость " + cloud.getSpeed() + " вне диапазона [3, 5]");
            }
        }

        g.dispose();
    }

    @Test
    @DisplayName("Облака всегда есть в списке (их не становится 0)")
    void cloudsNeverRunOut() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        for (int i = 0; i < 1000; i++) {
            landscape.draw(g);
            assertFalse(landscape.getClouds().isEmpty());
        }

        g.dispose();
    }

    // ---------- вспомогательные методы ----------

    private BufferedImage newImage() {
        return new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    private BufferedImage render(final Landscape l) {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();
        l.draw(g);
        g.dispose();
        return image;
    }

    private int brightness(final int rgb) {
        return ((rgb >> 16) & 0xFF) + ((rgb >> 8) & 0xFF) + (rgb & 0xFF);
    }

    private int countColor(final BufferedImage image, final Color color) {
        final int rgb = color.getRGB();
        int count = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) == rgb) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean sameImage(final BufferedImage a, final BufferedImage b) {
        for (int x = 0; x < a.getWidth(); x++) {
            for (int y = 0; y < a.getHeight(); y++) {
                if (a.getRGB(x, y) != b.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
}
