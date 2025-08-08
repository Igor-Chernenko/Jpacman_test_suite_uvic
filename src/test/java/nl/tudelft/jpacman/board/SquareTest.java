package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the abstract Square base class.
 */
class SquareTest {

    /** Minimal concrete Square so we can instantiate it in tests. */
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

    @Test
    void linkIsOneWayAndGetSquareAtWorks() {
        Sprite s = Mockito.mock(Sprite.class);

        Square a = new TestSquare(s, true);
        Square b = new TestSquare(s, true);

        // nothing linked yet
        assertNull(a.getSquareAt(Direction.EAST));
        assertNull(b.getSquareAt(Direction.WEST));

        // link one way: a --EAST--> b
        a.link(b, Direction.EAST);

        assertSame(b, a.getSquareAt(Direction.EAST), "a.EAST should be b");
        assertNull(b.getSquareAt(Direction.WEST), "Linking is one-way unless we link back explicitly");

        // now link back
        b.link(a, Direction.WEST);
        assertSame(a, b.getSquareAt(Direction.WEST), "b.WEST should be a after linking back");
    }

    @Test
    void occupantsListIsImmutableAndOrdered() {
        Sprite s = Mockito.mock(Sprite.class);
        Square sq = new TestSquare(s, true);

        Unit u1 = Mockito.mock(Unit.class);
        Unit u2 = Mockito.mock(Unit.class);

        // keep invariant true: occupants claim no square yet
        Mockito.when(u1.hasSquare()).thenReturn(false);
        Mockito.when(u2.hasSquare()).thenReturn(false);

        sq.put(u1);
        sq.put(u2);

        List<Unit> occ = sq.getOccupants();
        assertEquals(2, occ.size());
        assertSame(u1, occ.get(0), "Oldest occupant should be first");
        assertSame(u2, occ.get(1), "Next occupant should be second");

        // immutable copy
        assertThrows(UnsupportedOperationException.class, () -> occ.add(Mockito.mock(Unit.class)));
    }

    @Test
    void removeActuallyRemovesOccupant() {
        Sprite s = Mockito.mock(Sprite.class);
        Square sq = new TestSquare(s, true);

        Unit u1 = Mockito.mock(Unit.class);
        Unit u2 = Mockito.mock(Unit.class);
        Mockito.when(u1.hasSquare()).thenReturn(false);
        Mockito.when(u2.hasSquare()).thenReturn(false);

        sq.put(u1);
        sq.put(u2);

        sq.remove(u1);

        List<Unit> occ = sq.getOccupants();
        assertEquals(1, occ.size());
        assertSame(u2, occ.get(0), "After removing u1, only u2 should remain");
    }

    @Test
    void invariantTrueWhenOccupantReferencesThisSquare() {
        Sprite s = Mockito.mock(Sprite.class);
        Square sq = new TestSquare(s, true);

        Unit u = Mockito.mock(Unit.class);
        Mockito.when(u.hasSquare()).thenReturn(true);
        Mockito.when(u.getSquare()).thenReturn(sq); // occupant points to this square

        sq.put(u);

        assertTrue(sq.invariant(), "Invariant should hold when occupant's square == this");
    }

    @Test
    void invariantFalseWhenOccupantReferencesDifferentSquare() {
        Sprite s = Mockito.mock(Sprite.class);
        Square here = new TestSquare(s, true);
        Square elsewhere = new TestSquare(s, true);

        Unit u = Mockito.mock(Unit.class);
        Mockito.when(u.hasSquare()).thenReturn(true);
        Mockito.when(u.getSquare()).thenReturn(elsewhere); // inconsistent

        here.put(u);

        assertFalse(here.invariant(), "Invariant should fail when an occupant claims a different square");
    }
}
