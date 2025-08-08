package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class GameTest
{
    @Test
    void testProgressStartStop()
    {
        Player player = Mockito.mock(Player.class);
        Level level = Mockito.mock(Level.class);
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(true);
        Mockito.when(level.remainingPellets()).thenReturn(1);
        PointCalculator pointCalculator = new DefaultPointCalculator();

        SinglePlayerGame game = new SinglePlayerGame(player, level, pointCalculator);

        game.start();
        assertThat(game.isInProgress()).isTrue();

        game.stop();
        assertThat(game.isInProgress()).isFalse();
    }

    @Test
    void testMove()
    {
        Player player = Mockito.mock(Player.class);
        Level level = Mockito.mock(Level.class);
        Mockito.when(level.isAnyPlayerAlive()).thenReturn(true);
        Mockito.when(level.remainingPellets()).thenReturn(1);
        PointCalculator pointCalculator = new DefaultPointCalculator();

        SinglePlayerGame game = new SinglePlayerGame(player, level, pointCalculator);

        game.start();

        game.move(game.getPlayers().getFirst(), Direction.NORTH);

        verify(level).move(game.getPlayers().getFirst(), Direction.NORTH);
    }
}
