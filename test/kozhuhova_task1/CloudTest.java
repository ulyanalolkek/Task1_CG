package kozhuhova_task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CloudTest {

    private static final int X = 100;
    private static final int Y = 50;
    private static final int SIZE = 40;
    private static final int SPEED = 5;

    private Cloud cloud;

    @BeforeEach
    void setUp() {
        cloud = new Cloud(X, Y, SIZE, SPEED);
    }

    @Test
    @DisplayName("Конструктор сохраняет все параметры")
    void constructorStoresValues() {
        assertEquals(X, cloud.getX());
        assertEquals(Y, cloud.getY());
        assertEquals(SIZE, cloud.getSize());
        assertEquals(SPEED, cloud.getSpeed());
    }

    @Test
    @DisplayName("setX меняет только координату X")
    void setXChangesOnlyX() {
        cloud.setX(300);

        assertEquals(300, cloud.getX());
        assertEquals(Y, cloud.getY());
        assertEquals(SIZE, cloud.getSize());
        assertEquals(SPEED, cloud.getSpeed());
    }

    @Test
    @DisplayName("setY меняет только координату Y")
    void setYChangesOnlyY() {
        cloud.setY(80);

        assertEquals(X, cloud.getX());
        assertEquals(80, cloud.getY());
    }

    @Test
    @DisplayName("setSize сохраняет значение")
    void setSizeStoresValue() {
        cloud.setSize(70);
        assertEquals(70, cloud.getSize());
    }

    @Test
    @DisplayName("setSpeed сохраняет значение")
    void setSpeedStoresValue() {
        cloud.setSpeed(3);
        assertEquals(3, cloud.getSpeed());
    }

    @Test
    @DisplayName("Облако принимает отрицательные значения")
    void negativeValuesAreAccepted() {
        cloud.setX(-150);
        cloud.setY(-10);
        cloud.setSpeed(-3);

        assertEquals(-150, cloud.getX());
        assertEquals(-10, cloud.getY());
        assertEquals(-3, cloud.getSpeed());
    }

    @Test
    @DisplayName("draw() не выбрасывает исключений")
    void drawDoesNotThrow() {
        final BufferedImage image = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> cloud.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("draw() рисует полупрозрачные белые пиксели (alpha = 218)")
    void drawProducesTranslucentWhitePixels() {
        final BufferedImage image = drawCloud(new Cloud(100, 100, 100, 3));

        // (110, 100) принадлежит только одному овалу, поэтому alpha не складывается
        final int argb = image.getRGB(110, 100);
        assertEquals(218, argb >>> 24, "Прозрачность облака должна быть 218");
        assertEquals(0xFFFFFF, argb & 0xFFFFFF, "Облако должно быть белым");
    }

    @Test
    @DisplayName("draw() не закрашивает пиксели вдали от облака")
    void drawLeavesFarPixelsEmpty() {
        final BufferedImage image = drawCloud(new Cloud(100, 100, 100, 3));

        assertEquals(0, image.getRGB(10, 10) >>> 24);
        assertEquals(0, image.getRGB(390, 290) >>> 24);
    }

    @Test
    @DisplayName("Облако рисуется там, где задан x")
    void drawFollowsX() {
        final BufferedImage left = drawCloud(new Cloud(50, 100, 100, 3));
        final BufferedImage right = drawCloud(new Cloud(150, 100, 100, 3));

        assertEquals(0, left.getRGB(220, 110) >>> 24,
                "Облако с x = 50 не достаёт до x = 220");
        assertTrue(right.getRGB(220, 110) >>> 24 >= 218,
                "Облако с x = 150 должно закрывать x = 220");
    }

    @Test
    @DisplayName("Облако рисуется там, где задан y")
    void drawFollowsY() {
        final BufferedImage high = drawCloud(new Cloud(100, 30, 100, 3));
        final BufferedImage low = drawCloud(new Cloud(100, 200, 100, 3));

        assertEquals(218, high.getRGB(110, 30) >>> 24);
        assertEquals(0, high.getRGB(110, 200) >>> 24);
        assertEquals(218, low.getRGB(110, 200) >>> 24);
        assertEquals(0, low.getRGB(110, 30) >>> 24);
    }

    @Test
    @DisplayName("Облако большего размера занимает больше пикселей")
    void biggerCloudCoversMorePixels() {
        final int small = countOpaque(drawCloud(new Cloud(100, 100, 50, 3)));
        final int big = countOpaque(drawCloud(new Cloud(100, 100, 150, 3)));

        assertTrue(big > small, "Большое облако должно занимать больше места");
    }

    @Test
    @DisplayName("draw() работает при нулевом размере и отрицательных координатах")
    void drawWithZeroSizeAndNegativeCoordinates() {
        final BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> new Cloud(0, 0, 0, 0).draw(g));
        assertDoesNotThrow(() -> new Cloud(-200, -200, 70, 3).draw(g));

        g.dispose();
    }

    private BufferedImage drawCloud(final Cloud c) {
        final BufferedImage image = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();
        c.draw(g);
        g.dispose();
        return image;
    }

    private int countOpaque(final BufferedImage image) {
        int count = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if ((image.getRGB(x, y) >>> 24) != 0) {
                    count++;
                }
            }
        }
        return count;
    }
}
