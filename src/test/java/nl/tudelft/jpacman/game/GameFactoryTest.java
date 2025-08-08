package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class GameFactoryTest
{
    @Test
    void isSinglePlayer()
    {
        PacManSprites pacManSprites = new PacManSprites();
        PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
        GameFactory gameFactory = new GameFactory(playerFactory);

        PointCalculator pointCalculator = new DefaultPointCalculator();
        Level level = Mockito.mock(Level.class);
        Game game = gameFactory.createSinglePlayerGame(level, pointCalculator);

        assertThat(game).isInstanceOf(SinglePlayerGame.class);
    }

    @Test
    void testPlayerCreated()
    {
        PacManSprites pacManSprites = new PacManSprites();
        PlayerFactory playerFactory = Mockito.spy(new PlayerFactory(pacManSprites));
        GameFactory gameFactory = new GameFactory(playerFactory);

        PointCalculator pointCalculator = new DefaultPointCalculator();
        Level level = Mockito.mock(Level.class);
        gameFactory.createSinglePlayerGame(level, pointCalculator);

        verify(playerFactory).createPacMan();
    }
}
