package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Thorough tests for Unit behavior.
 */
class UnitTest {

    /** Minimal concrete Square so we can build topologies. */
    private static final class TestSquare extends Square {
        private final Sprite sprite;
        private final boolean accessible;

        TestSquare(Sprite sprite, boolean accessible) {
            this.sprite = sprite;
            this.accessible = accessible;
        }

        @Override
        public boolean isAccessibleTo(Unit unit) {
            return accessible;
        }

        @Override
        public Sprite getSprite() {
            return sprite;
        }
    }

    /** Minimal concrete Unit so we can instantiate and test the abstract class. */
    private static final class TestUnit extends Unit {
        private final Sprite sprite;

        TestUnit(Sprite sprite) {
            super();
            this.sprite = sprite;
        }

        @Override
        public Sprite getSprite() {
            return sprite;
        }
    }

    @Test
    void defaultsToFacingEast() {
        Sprite sp = Mockito.mock(Sprite.class);
        Unit u = new TestUnit(sp);
        assertEquals(Direction.EAST, u.getDirection(), "New units should face EAST by default.");
    }

    @Test
    void setAndGetDirection() {
        Sprite sp = Mockito.mock(Sprite.class);
        Unit u = new TestUnit(sp);

        u.setDirection(Direction.NORTH);
        assertEquals(Direction.NORTH, u.getDirection());

        u.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, u.getDirection());
    }

    @Test
    void hasSquareTogglesWithOccupyAndLeave() {
        Sprite sp = Mockito.mock(Sprite.class);
        Unit u = new TestUnit(sp);

        assertFalse(u.hasSquare(), "Initially not on any square.");

        TestSquare s = new TestSquare(sp, true);
        u.occupy(s);

        assertTrue(u.hasSquare(), "After occupy, should be on a square.");
        assertSame(s, u.getSquare(), "getSquare should return the square we occupied.");

        u.leaveSquare();
        assertFalse(u.hasSquare(), "After leaving, should not be on a square.");
    }

    @Test
    void occupyFirstTimeAddsToOccupants() {
        Sprite sp = Mockito.mock(Sprite.class);
        Unit u = new TestUnit(sp);
        TestSquare s = new TestSquare(sp, true);

        u.occupy(s);

        assertTrue(s.getOccupants().contains(u), "Square should list the unit as an occupant.");
        assertTrue(u.invariant(), "Invariant holds when square contains the unit.");
    }

    @Test
    void occupyAgainMovesAndCleansUpPreviousSquare() {
        Sprite sp = Mockito.mock(Sprite.class);
        Unit u = new TestUnit(sp);

        TestSquare s1 = new TestSquare(sp, true);
        TestSquare s2 = new TestSquare(sp, true);

        u.occupy(s1);
        u.occupy(s2);

        assertFalse(s1.getOccupants().contains(u), "Old square should no longer contain the unit.");
        assertTrue(s2.getOccupants().contains(u), "New square should contain the unit.");
        assertSame(s2, u.getSquare());
    }

    @Test
    void leaveSquareWhenOnSquareRemovesAndNullsOut() {
        Sprite sp = Mockito.mock(Sprite.class);
        Unit u = new TestUnit(sp);
        TestSquare s = new TestSquare(sp, true);

        u.occupy(s);
        u.leaveSquare();

        assertFalse(s.getOccupants().contains(u), "Leaving should remove unit from occupants.");
        assertFalse(u.hasSquare(), "Unit should no longer be on a square.");
        assertTrue(u.invariant(), "Invariant holds when not on a square.");
    }

    @Test
    void leaveSquareWhenAlreadyOffBoardIsNoOp() {
        Sprite sp = Mockito.mock(Sprite.class);
        Unit u = new TestUnit(sp);

        // Should not throw, remains without a square
        u.leaveSquare();
        assertFalse(u.hasSquare());
        assertTrue(u.invariant());
    }

    @Test
    void invariantFalseWhenUnitNotListedOnItsSquare() {
        Sprite sp = Mockito.mock(Sprite.class);
        Unit u = new TestUnit(sp);
        TestSquare s = new TestSquare(sp, true);

        u.occupy(s);
        // Force inconsistency: remove directly via package-visible API
        s.remove(u);

        assertFalse(u.invariant(), "Invariant must fail if unit's square does not list the unit.");
    }

    @Test
    void squaresAheadOfZeroReturnsCurrentSquare() {
        Sprite sp = Mockito.mock(Sprite.class);
        Unit u = new TestUnit(sp);

        TestSquare a = new TestSquare(sp, true);
        u.occupy(a);

        assertSame(a, u.squaresAheadOf(0), "0 steps ahead should be the current square.");
    }

    @Test
    void squaresAheadOfFollowsDirectionOverMultipleSteps() {
        Sprite sp = Mockito.mock(Sprite.class);
        Unit u = new TestUnit(sp);

        // Build a small line: a <-> b <-> c
        TestSquare a = new TestSquare(sp, true);
        TestSquare b = new TestSquare(sp, true);
        TestSquare c = new TestSquare(sp, true);

        a.link(b, Direction.EAST);
        b.link(c, Direction.EAST);
        b.link(a, Direction.WEST);
        c.link(b, Direction.WEST);

        u.occupy(a);

        // EAST 2 steps: a -> b -> c
        u.setDirection(Direction.EAST);
        assertSame(c, u.squaresAheadOf(2));

        // WEST 2 steps: from c -> b -> a
        u.occupy(c); // start at c now
        u.setDirection(Direction.WEST);
        assertSame(a, u.squaresAheadOf(2));
    }
}
