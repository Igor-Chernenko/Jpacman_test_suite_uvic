package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BoardFactoryTest {

    @Test
    void createGroundUsesSpriteAndIsAccessible() {
        // Arrange
        PacManSprites sprites = Mockito.mock(PacManSprites.class);
        Sprite groundSprite = Mockito.mock(Sprite.class);
        Mockito.when(sprites.getGroundSprite()).thenReturn(groundSprite);

        BoardFactory factory = new BoardFactory(sprites);

        // Act
        Square ground = factory.createGround();

        // Assert
        assertSame(groundSprite, ground.getSprite(), "Ground must use the ground sprite provided by PacManSprites.");
        Unit dummyUnit = Mockito.mock(Unit.class);
        assertTrue(ground.isAccessibleTo(dummyUnit), "Ground should be accessible to any unit.");
    }

    @Test
    void createWallUsesSpriteAndIsNotAccessible() {
        // Arrange
        PacManSprites sprites = Mockito.mock(PacManSprites.class);
        Sprite wallSprite = Mockito.mock(Sprite.class);
        Mockito.when(sprites.getWallSprite()).thenReturn(wallSprite);

        BoardFactory factory = new BoardFactory(sprites);

        // Act
        Square wall = factory.createWall();

        // Assert
        assertSame(wallSprite, wall.getSprite(), "Wall must use the wall sprite provided by PacManSprites.");
        Unit dummyUnit = Mockito.mock(Unit.class);
        assertFalse(wall.isAccessibleTo(dummyUnit), "Wall should not be accessible to any unit.");
    }

    @Test
    void createBoardKeepsSquaresAndLinksWithWrapAround() {
        // Arrange
        PacManSprites sprites = Mockito.mock(PacManSprites.class);
        // Sprites aren't used by createBoard, but we need a factory:
        BoardFactory factory = new BoardFactory(sprites);

        int width = 3;
        int height = 2;

        // Fill grid with distinct squares from createGround()
        Square[][] grid = new Square[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = factory.createGround();
            }
        }

        // Act
        Board board = factory.createBoard(grid);

        // Basic shape checks
        assertEquals(width, board.getWidth(), "Board width should match the grid width.");
        assertEquals(height, board.getHeight(), "Board height should match the grid height.");

        // The board should expose the same Square instances as in the grid
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                assertSame(grid[x][y], board.squareAt(x, y),
                        "Board must wrap exactly the given square grid.");
            }
        }

        // Neighbour linking with wrap-around (toroidal) topology
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Square here = board.squareAt(x, y);

                for (Direction dir : Direction.values()) {
                    int dx = (width + x + dir.getDeltaX()) % width;
                    int dy = (height + y + dir.getDeltaY()) % height;
                    Square expectedNeighbour = grid[dx][dy];

                    // Square should have a getter to retrieve the linked neighbour per direction
                    Square actualNeighbour = here.getSquareAt(dir);

                    assertSame(expectedNeighbour, actualNeighbour,
                            "Square (" + x + "," + y + ") should link " + dir +
                                    " to (" + dx + "," + dy + ") with wrap-around.");
                }
            }
        }
    }
}
