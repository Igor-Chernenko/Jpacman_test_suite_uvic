package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen
 *
 */

class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        Square target = new BasicSquare();
        unit.occupy(target);
        assertThat(unit.getSquare()).isEqualTo(target);
        assertThat(target.getOccupants().contains(unit)).isTrue();
    }

    /**
     * tes the target square as its base after occupation
     *
     */
    @Test
    void testOccupy() {
        Square target = new BasicSquare();
        unit.occupy(target);

        // The assignment did not make it clear what exactly is to be tested here so we tested both
        assertThat(unit.getSquare()).isEqualTo(target);
        assertThat(target.getOccupants().contains(unit)).isTrue();
    }

    /**
     * test that the unit  has the target square as its base after
     * double occupation.
     */
    @Test
    void testReoccupy() {
        Square target = new BasicSquare();
        unit.occupy(target);
        BasicUnit unit2 = new BasicUnit();
        unit2.occupy(target);
        assertThat(unit.getSquare()).isEqualTo(target);
        assertThat(target.getOccupants().contains(unit)).isTrue();
    }
}
