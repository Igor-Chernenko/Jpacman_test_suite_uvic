package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// tests what clyde does
public class ClydeTest extends GhostTest {

    // no player on map so no move
    @Test
    void noPlayerTest() {
        Level level = getLevelFromMapResource("/only_clyde_map.txt");
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        assertThat(clyde.nextAiMove()).isEmpty();
    }

    // player is close so clyde moves east
    @Test
    void nextToPlayerTest() {
        Level level = getLevelFromMapResource("/clyde_pacman_dist1_map.txt");
        level.registerPlayer(getPlayer());
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }

    // player is far so clyde moves west
    @Test
    void nineFromPlayerTest() {
        Level level = getLevelFromMapResource("/clyde_pacman_dist9_map.txt");
        level.registerPlayer(getPlayer());
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }
}
