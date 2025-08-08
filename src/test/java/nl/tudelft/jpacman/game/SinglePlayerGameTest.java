package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class SinglePlayerGameTest
{
    @Test
    void testSinglePlayer()
    {
        Player player = Mockito.mock(Player.class);
        Level level = Mockito.mock(Level.class);
        PointCalculator pointCalculator = new DefaultPointCalculator();

        SinglePlayerGame game = new SinglePlayerGame(player, level, pointCalculator);

        assertThat(game.getPlayers().size()).isEqualTo(1);
    }
}
