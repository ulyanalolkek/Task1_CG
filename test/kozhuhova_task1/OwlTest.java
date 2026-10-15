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

class OwlTest {

    private static final int OWL_X = 200;
    private static final int OWL_Y = 200;
    private static final int OWL_WIDTH = 400;
    private static final int OWL_HEIGHT = 500;

    private static final int IMAGE_WIDTH = 1000;
    private static final int IMAGE_HEIGHT = 750;

    private static final Color MAIN_COLOR = new Color(128, 0, 128);
    private static final Color LIGHT_COLOR = new Color(160, 32, 160);
    private static final Color DARK_COLOR = new Color(100, 0, 100);
    private static final Color BEAK_COLOR = new Color(240, 160, 60);
    private static final Color ROPE_COLOR = new Color(210, 105, 30);

    private Owl owl;

    @BeforeEach
    void setUp() {
        owl = new Owl(OWL_X, OWL_Y, OWL_WIDTH, OWL_HEIGHT,
                MAIN_COLOR, LIGHT_COLOR, DARK_COLOR, 0, 0, true);
    }

    // ---------- состояние ----------

    @Test
    @DisplayName("Конструктор сохраняет параметры")
    void constructorStoresValues() {
        final Owl o = new Owl(10, 20, 30, 40,
                MAIN_COLOR, LIGHT_COLOR, DARK_COLOR, 7, 9, false);

        assertEquals(10, o.getX());
        assertEquals(20, o.getY());
        assertEquals(30, o.getWidth());
        assertEquals(40, o.getHeight());
        assertEquals(7, o.getAngle());
        assertEquals(9, o.getTick());
        assertFalse(o.isOwlJump());
    }

    @Test
    @DisplayName("Сеттеры координат и размеров сохраняют значения")
    void geometrySettersStoreValues() {
        owl.setX(11);
        owl.setY(22);
        owl.setWidth(33);
        owl.setHeight(44);

        assertEquals(11, owl.getX());
        assertEquals(22, owl.getY());
        assertEquals(33, owl.getWidth());
        assertEquals(44, owl.getHeight());
    }

    @Test
    @DisplayName("setAngle и setTick сохраняют значения")
    void angleAndTickSettersStoreValues() {
        owl.setAngle(-45);
        owl.setTick(77);

        assertEquals(-45, owl.getAngle());
        assertEquals(77, owl.getTick());
    }

    @Test
    @DisplayName("setOwlJump переключает режим")
    void setOwlJumpTogglesFlag() {
        owl.setOwlJump(false);
        assertFalse(owl.isOwlJump());

        owl.setOwlJump(true);
        assertTrue(owl.isOwlJump());
    }

    // ---------- отрисовка ----------

    @Test
    @DisplayName("draw() не выбрасывает исключений")
    void drawDoesNotThrow() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> owl.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("draw() рисует непрозрачные пиксели")
    void drawProducesOpaquePixels() {
        assertTrue(countOpaque(render(owl)) > 0,
                "После draw() должны быть непрозрачные пиксели");
    }

    @Test
    @DisplayName("Сова рисуется своим основным цветом")
    void drawUsesMainColor() {
        assertTrue(countColor(render(owl), MAIN_COLOR) > 0,
                "На рисунке должен быть основной цвет совы");
    }

    @Test
    @DisplayName("Сова рисует светлый цвет (живот и лицо)")
    void drawUsesLightColor() {
        assertTrue(countColor(render(owl), LIGHT_COLOR) > 0,
                "На рисунке должен быть светлый цвет совы");
    }

    @Test
    @DisplayName("Сова рисует клюв")
    void drawProducesBeak() {
        assertTrue(countColor(render(owl), BEAK_COLOR) > 0,
                "На рисунке должен быть клюв");
    }

    @Test
    @DisplayName("Смена цвета через setColorMain меняет рисунок")
    void setColorMainChangesImage() {
        final Color newColor = new Color(0, 0, 255);
        owl.setColorMain(newColor);

        final BufferedImage image = render(owl);

        assertTrue(countColor(image, newColor) > 0,
                "Новый основной цвет должен появиться на рисунке");
        assertEquals(0, countColor(image, MAIN_COLOR),
                "Старого основного цвета быть не должно");
    }

    @Test
    @DisplayName("Скакалка рисуется в режиме прыжка")
    void ropeIsDrawnWhenJumping() {
        owl.setOwlJump(true);
        owl.setTick(50);

        assertTrue(countColor(render(owl), ROPE_COLOR) > 0,
                "Скакалка должна быть видна при isOwlJump = true");
    }

    @Test
    @DisplayName("Скакалка рисуется и когда сова не прыгает")
    void ropeIsDrawnWhenNotJumping() {
        // в этом режиме скакалка рисуется ПОД телом, поэтому берём положение,
        // при котором она выходит за границы туловища
        owl.setOwlJump(false);
        owl.setTick(100);

        assertTrue(countColor(render(owl), ROPE_COLOR) > 0,
                "Скакалка должна быть видна при isOwlJump = false");
    }

    @Test
    @DisplayName("Изменение tick меняет форму скакалки")
    void tickChangesRopeShape() {
        owl.setTick(10);
        final BufferedImage first = render(owl);

        owl.setTick(90);
        final BufferedImage second = render(owl);

        assertFalse(sameImage(first, second),
                "При разных tick скакалка должна выглядеть по-разному");
    }

    @Test
    @DisplayName("Изменение угла крыльев меняет рисунок")
    void angleChangesImage() {
        owl.setAngle(14);
        final BufferedImage first = render(owl);

        owl.setAngle(47);
        final BufferedImage second = render(owl);

        assertFalse(sameImage(first, second),
                "При разных углах крылья должны выглядеть по-разному");
    }

    @Test
    @DisplayName("Одинаковое состояние даёт одинаковый рисунок")
    void sameStateGivesSameImage() {
        owl.setAngle(30);
        owl.setTick(40);

        assertTrue(sameImage(render(owl), render(owl)),
                "Отрисовка детерминирована");
    }

    @Test
    @DisplayName("setX сдвигает сову вправо")
    void setXMovesOwlRight() {
        final int leftBefore = leftmostOpaqueX(render(owl));

        owl.setX(OWL_X + 100);
        final int leftAfter = leftmostOpaqueX(render(owl));

        assertEquals(100, leftAfter - leftBefore, 3,
                "Левая граница совы должна сдвинуться примерно на 100 px");
    }

    @Test
    @DisplayName("setY сдвигает сову вниз")
    void setYMovesOwlDown() {
        owl.setOwlJump(false);
        final int topBefore = topmostOpaqueY(render(owl));

        owl.setY(OWL_Y + 50);
        final int topAfter = topmostOpaqueY(render(owl));

        assertTrue(topAfter > topBefore,
                "После увеличения Y верх совы должен опуститься");
    }

    @Test
    @DisplayName("Бóльшие размеры дают бóльше закрашенных пикселей")
    void biggerOwlCoversMorePixels() {
        final Owl small = new Owl(100, 100, 200, 250,
                MAIN_COLOR, LIGHT_COLOR, DARK_COLOR, 0, 0, true);
        final Owl big = new Owl(100, 100, 400, 500,
                MAIN_COLOR, LIGHT_COLOR, DARK_COLOR, 0, 0, true);

        assertTrue(countOpaque(render(big)) > countOpaque(render(small)),
                "Большая сова должна занимать больше пикселей");
    }

    @Test
    @DisplayName("draw() восстанавливает трансформацию Graphics2D")
    void drawRestoresTransform() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();
        final AffineTransform before = g.getTransform();

        owl.setAngle(30);
        owl.draw(g);

        assertEquals(before, g.getTransform(),
                "draw() должен восстанавливать исходную трансформацию");
        g.dispose();
    }

    @Test
    @DisplayName("draw() работает при нулевых размерах")
    void drawWithZeroSize() {
        final Owl zero = new Owl(0, 0, 0, 0,
                MAIN_COLOR, LIGHT_COLOR, DARK_COLOR, 0, 0, true);
        final BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> zero.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("draw() работает с отрицательными координатами")
    void drawWithNegativeCoordinates() {
        final Owl negative = new Owl(-300, -300, OWL_WIDTH, OWL_HEIGHT,
                MAIN_COLOR, LIGHT_COLOR, DARK_COLOR, 0, 0, false);
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> negative.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("draw() при разных углах, tick и режимах не падает")
    void drawWithVariousStates() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> {
            for (final boolean jump : new boolean[]{true, false}) {
                owl.setOwlJump(jump);
                for (int angle = -90; angle <= 90; angle += 15) {
                    for (int tick = 0; tick <= 100; tick += 25) {
                        owl.setAngle(angle);
                        owl.setTick(tick);
                        owl.draw(g);
                    }
                }
            }
        });

        g.dispose();
    }

    // ---------- вспомогательные методы ----------

    private BufferedImage newImage() {
        return new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    private BufferedImage render(final Owl o) {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();
        o.draw(g);
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

    private int leftmostOpaqueX(final BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if ((image.getRGB(x, y) >>> 24) != 0) {
                    return x;
                }
            }
        }
        return -1;
    }

    private int topmostOpaqueY(final BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if ((image.getRGB(x, y) >>> 24) != 0) {
                    return y;
                }
            }
        }
        return -1;
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
