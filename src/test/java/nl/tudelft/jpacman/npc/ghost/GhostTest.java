package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

// base setup for ghost tests
public class GhostTest {

    private GhostMapParser mapParser;
    private Player player;

    // gets the player made for tests
    protected Player getPlayer() {
        return player;
    }

    // makes stuff before each test
    @BeforeEach
    void setup() {
        PacManSprites sprites = new PacManSprites();

        PlayerFactory playerFactory = new PlayerFactory(sprites);
        player = playerFactory.createPacMan();

        GhostFactory ghostFactory = new GhostFactory(sprites);
        PointCalculator pointCalculator = new DefaultPointCalculator();
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        BoardFactory boardFactory = new BoardFactory(sprites);

        mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }

    // loads a level from the map name
    protected Level getLevelFromMapResource(String mapName) {
        try {
            return mapParser.parseMap(mapName);
        } catch (IOException ioe) {
            fail("could not load test map", ioe);
            return null;
        }
    }
}
