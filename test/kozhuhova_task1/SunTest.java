package kozhuhova_task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SunTest {

    private Sun sun;

    @BeforeEach
    void setUp() {
        sun = new Sun(1, 2, 3);
    }

    @Test
    @DisplayName("Конструктор сохраняет параметры")
    void constructorStoresValues() {
        assertEquals(1, sun.getX());
        assertEquals(2, sun.getY());
        assertEquals(3, sun.getTick());
    }

    @Test
    @DisplayName("Сеттеры сохраняют значения")
    void settersStoreValues() {
        sun.setX(10);
        sun.setY(20);
        sun.setTick(30);

        assertEquals(10, sun.getX());
        assertEquals(20, sun.getY());
        assertEquals(30, sun.getTick());
    }

    @Test
    @DisplayName("draw() не выбрасывает исключений")
    void drawDoesNotThrow() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> sun.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("Диск солнца жёлтый")
    void discIsYellow() {
        final BufferedImage image = render(new Sun(0, 0, 0));

        assertEquals(Color.YELLOW.getRGB(), image.getRGB(65, 65));
        assertEquals(Color.YELLOW.getRGB(), image.getRGB(50, 65));
        assertEquals(Color.YELLOW.getRGB(), image.getRGB(80, 65));
    }

    @Test
    @DisplayName("Вне диска и лучей ничего не рисуется")
    void nothingDrawnFarAway() {
        final BufferedImage image = render(new Sun(0, 0, 0));

        assertEquals(0, image.getRGB(299, 299) >>> 24);
        assertEquals(0, image.getRGB(200, 20) >>> 24);
    }

    @Test
    @DisplayName("Верхний луч виден при tick = 0")
    void topRayVisibleAtTickZero() {
        final BufferedImage image = render(new Sun(0, 0, 0));

        assertTrue(hasPixelInRect(image, 63, 10, 67, 28),
                "Верхний луч должен быть виден над диском");
    }

    @Test
    @DisplayName("Лучи поворачиваются при изменении tick")
    void raysRotateWithTick() {
        final BufferedImage first = render(new Sun(0, 0, 0));
        final BufferedImage second = render(new Sun(0, 0, 45));

        assertFalse(sameImage(first, second), "Лучи должны выглядеть по-разному");
    }

    @Test
    @DisplayName("Поворот на 360 градусов даёт то же изображение")
    void fullRotationGivesSameImage() {
        final BufferedImage first = render(new Sun(0, 0, 0));
        final BufferedImage second = render(new Sun(0, 0, 360));

        assertTrue(sameImage(first, second));
    }

    @Test
    @DisplayName("Диск солнца не вращается: центр остаётся жёлтым при любом tick")
    void discStaysInPlace() {
        for (int tick = 0; tick < 360; tick += 30) {
            final BufferedImage image = render(new Sun(0, 0, tick));
            assertEquals(Color.YELLOW.getRGB(), image.getRGB(65, 65), "tick = " + tick);
        }
    }

    @Test
    @DisplayName("draw() восстанавливает трансформацию Graphics2D")
    void drawRestoresTransform() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();
        final AffineTransform before = g.getTransform();

        new Sun(0, 0, 77).draw(g);

        assertEquals(before, g.getTransform());
        g.dispose();
    }

    @Test
    @DisplayName("Отрицательный tick не вызывает ошибок")
    void negativeTickWorks() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> new Sun(0, 0, -90).draw(g));

        g.dispose();
    }

    private BufferedImage newImage() {
        return new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
    }

    private BufferedImage render(final Sun s) {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();
        s.draw(g);
        g.dispose();
        return image;
    }

    private boolean hasPixelInRect(final BufferedImage image,
                                   final int x1, final int y1, final int x2, final int y2) {
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                if ((image.getRGB(x, y) >>> 24) != 0) {
                    return true;
                }
            }
        }
        return false;
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
