package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the AI movement behaviour of Inky.
 */
public class InkyTest extends GhostTest {

    /**
     * Checks that if both the player and Blinky are not present the next AI move is empty.
     */
    @Test
    void onlyInkyTest() {

        Level level = getLevelFromMapResource("/only_inky_map.txt");
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEmpty();
    }

    /**
     * Checks that if the player is missing the next AI move is empty.
     */
    @Test
    void noPlayerTest() {

        Level level = getLevelFromMapResource("/inky_and_blinky_map.txt");
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEmpty();
    }

    /**
     * Checks that if Blinky is missing the next AI move is empty.
     */
    @Test
    void noBlinkyTest() {

        Level level = getLevelFromMapResource("/inky_and_player_map.txt");
        level.registerPlayer(getPlayer());

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEmpty();
    }

    /**
     * Checks that if the destination square is unreachable, the next AI move is empty. The
     * destination square is three squares to the east of the player because Blinky is directly to
     * the east of the player.
     */
    @Test
    void destinationUnreachableTest() {

        Level level = getLevelFromMapResource("/inky_dest_unreachable_map.txt");
        level.registerPlayer(getPlayer());

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEmpty();
    }

    /**
     * Checks that if the player is directly to the west of Blinky, and Inky is two squares to the
     * east of Blinky, the next AI move is empty. This is because the square Inky is on should
     * already be the destination.
     */
    @Test
    void onDestinationPlayerBlinkyAdjacentTest() {

        Level level = getLevelFromMapResource("/player_blinky_adjacent_row_map.txt");
        level.registerPlayer(getPlayer());

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEmpty();
    }

    /**
     * Checks that if Blinky is directly to the west of Inky, and the player is directly to the
     * west of Blinky, the next AI move is east. This is because the resulting destination should
     * be one square to the east, so Blinky will move away from the player.
     */
    @Test
    void playerBlinkyInkyAdjacentTest() {

        Level level = getLevelFromMapResource("/player_blinky_inky_adjacent_map.txt");
        level.registerPlayer(getPlayer());

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }

    /**
     * Checks that if Blinky is directly to the west of the player and the player is directly to
     * the west of Inky, the next AI move is east. This is because the resulting destination should
     * be four squares to the east, so Blinky will move away from the player.
     */
    @Test
    void blinkyPlayerInkyAdjacentTest() {

        Level level = getLevelFromMapResource("/blinky_player_inky_adjacent_map.txt");
        level.registerPlayer(getPlayer());

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }

    /**
     * Checks that if the destination of the second part of the path lies beyond the map border,
     * the location gets wrapped around on the same row. In this specific case the location should
     * end up directly north of the current position.
     */
    @Test
    void destinationWrapAroundTest() {

        Level level = getLevelFromMapResource("/inky_dest_wraparound_map.txt");
        level.registerPlayer(getPlayer());

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.NORTH));
    }
}