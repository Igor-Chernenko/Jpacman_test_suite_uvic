package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * tests the withinBorders method of the Board class
 */
public class WithinbordersTest {

    private static final int BOARD_X_SIZE = 4;
    private static final int BOARD_Y_SIZE = 4;

    private Board board;


     //sets up a new 4x4 board for every test.

    @BeforeEach
    void setup() {

        board = new Board(generateBoardArray(BOARD_X_SIZE, BOARD_Y_SIZE));
    }

    /**
     *
     * @param xCoordinates The x coordinate.
     * @param yCoordinates The y coordinate.
     */
    @ParameterizedTest
    @CsvSource({
            "0, 2",
            "3, 2",
            "2, 0",
            "2, 3"
    })
    void validCoordinatesTest(int xCoordinates, int yCoordinates) {
        assertThat(board.withinBorders(xCoordinates, yCoordinates)).isTrue();
    }
    /**
     * Checks that coordinates that fall outside the 4x4 matrix return false.
     * @param xCoordinates The x coordinate.
     * @param yCoordinates The y coordinate.
     */
    @ParameterizedTest
    @CsvSource({
            "-1, 2",
            "4, 2",
            "2, -1",
            "2, 4"
    })
    void invalidCoordinatesTest(int xCoordinates, int yCoordinates) {
        assertThat(board.withinBorders(xCoordinates, yCoordinates)).isFalse();
    }

    private static Square[][] generateBoardArray(int xSize, int ySize) {

        Square[][] array = new Square[xSize][ySize];

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                array[i][j] = new BasicSquare();
            }
        }

        return array;
    }

}