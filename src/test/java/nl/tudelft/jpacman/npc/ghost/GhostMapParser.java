package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.npc.Ghost;

import java.util.List;

// this class helps put ghosts in maps for testing
public final class GhostMapParser extends MapParser {
    private final GhostFactory ghostFactory;

    // makes a new map parser that can add ghosts
    public GhostMapParser(LevelFactory levelFactory, BoardFactory boardFactory,
                          GhostFactory ghostFactory) {
        super(levelFactory, boardFactory);
        this.ghostFactory = ghostFactory;
    }

    // puts stuff in the map depending on the letter
    @Override
    protected void addSquare(Square[][] grid, List<Ghost> ghosts,
                             List<Square> startPositions, int x, int y, char c) {
        switch (c) {
            case 'B': // blinky
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createBlinky());
                break;
            case 'C': // clyde
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createClyde());
                break;
            case 'I': // inky
                grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createInky());
                break;
            default:
                super.addSquare(grid, ghosts, startPositions, x, y, c);
        }
    }
}
