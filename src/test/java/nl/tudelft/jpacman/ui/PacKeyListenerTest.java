package nl.tudelft.jpacman.ui;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;

public class PacKeyListenerTest
{
    @Test
    void testAction()
    {
        Map<Integer, Action> actions = new HashMap<>();

        Action mockAction = Mockito.mock(Action.class);
        actions.put(KeyEvent.VK_UP, mockAction);

        PacKeyListener keyListener = new PacKeyListener(actions);

        KeyEvent event = new KeyEvent(new Canvas(),KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED);

        keyListener.keyPressed(event);
        verify(mockAction).doAction();
    }
}
