package kozhuhova_task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrdinaryTreeTest {

    private static final Color GREEN = new Color(0, 60, 0);
    private static final Color BROWN = new Color(110, 63, 0);

    private static final int X = 100;
    private static final int Y = 300;
    private static final int SIZE = 100;

    private OrdinaryTree tree;

    @BeforeEach
    void setUp() {
        tree = new OrdinaryTree(X, Y, SIZE, GREEN, BROWN);
    }

    @Test
    @DisplayName("Конструктор сохраняет координаты и размер")
    void constructorStoresValues() {
        assertEquals(X, tree.getX());
        assertEquals(Y, tree.getY());
        assertEquals(SIZE, tree.getSize());
    }

    @Test
    @DisplayName("Сеттеры сохраняют значения")
    void settersStoreValues() {
        tree.setX(11);
        tree.setY(22);
        tree.setSize(33);

        assertEquals(11, tree.getX());
        assertEquals(22, tree.getY());
        assertEquals(33, tree.getSize());
    }

    @Test
    @DisplayName("draw() не выбрасывает исключений")
    void drawDoesNotThrow() {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> tree.draw(g));

        g.dispose();
    }

    @Test
    @DisplayName("Крона рисуется зелёным цветом")
    void crownIsGreen() {
        final BufferedImage image = render(tree);

        assertEquals(GREEN.getRGB(), image.getRGB(100, 215));
    }

    @Test
    @DisplayName("Ствол рисуется коричневым цветом")
    void trunkIsBrown() {
        final BufferedImage image = render(tree);

        assertEquals(BROWN.getRGB(), image.getRGB(107, 290));
    }

    @Test
    @DisplayName("Вдали от дерева ничего не рисуется")
    void nothingDrawnFarAway() {
        final BufferedImage image = render(tree);

        assertEquals(0, image.getRGB(20, 20) >>> 24);
        assertEquals(0, image.getRGB(550, 550) >>> 24);
    }

    @Test
    @DisplayName("Цвета берутся из конструктора")
    void colorsComeFromConstructor() {
        final Color otherGreen = new Color(10, 200, 10);
        final Color otherBrown = new Color(200, 100, 10);
        final BufferedImage image = render(new OrdinaryTree(X, Y, SIZE, otherGreen, otherBrown));

        assertEquals(otherGreen.getRGB(), image.getRGB(100, 215));
        assertEquals(otherBrown.getRGB(), image.getRGB(107, 290));
    }

    @Test
    @DisplayName("Дерево сдвигается вместе с x и y")
    void treeFollowsPosition() {
        final BufferedImage moved = render(new OrdinaryTree(X + 100, Y + 50, SIZE, GREEN, BROWN));

        assertEquals(GREEN.getRGB(), moved.getRGB(200, 265));
        assertEquals(0, moved.getRGB(100, 215) >>> 24, "На старом месте ничего быть не должно");
    }

    @Test
    @DisplayName("Дерево большего размера занимает больше пикселей")
    void biggerTreeCoversMorePixels() {
        final int small = countOpaque(render(new OrdinaryTree(X, Y, 60, GREEN, BROWN)));
        final int big = countOpaque(render(new OrdinaryTree(X, Y, 120, GREEN, BROWN)));

        assertTrue(big > small, "Большое дерево должно занимать больше места");
    }

    @Test
    @DisplayName("draw() работает при нулевом размере и отрицательных координатах")
    void drawWithZeroSizeAndNegativeCoordinates() {
        final BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        assertDoesNotThrow(() -> new OrdinaryTree(0, 0, 0, GREEN, BROWN).draw(g));
        assertDoesNotThrow(() -> new OrdinaryTree(-100, -100, 300, GREEN, BROWN).draw(g));

        g.dispose();
    }

    private BufferedImage newImage() {
        return new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
    }

    private BufferedImage render(final OrdinaryTree t) {
        final BufferedImage image = newImage();
        final Graphics2D g = image.createGraphics();
        t.draw(g);
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
