package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTest {

    private Square square;

    /**
     * creates a new BasicSquare for each test method .
     */
    @BeforeEach
    public void setup() {
        square = new BasicSquare();
    }

    /**
     * tests board creation
     */
    @Test
    public void testBoard() {
        Board test = new Board(new Square[][] {{ square }});
        assertThat(test.squareAt(0, 0)).isEqualTo(square);
    }
}