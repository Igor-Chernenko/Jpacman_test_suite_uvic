package nl.tudelft.jpacman.ui;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class ButtonPanelTest
{
    @Test
    void addButton()
    {
        Map<String, Action> actions = new HashMap<>();
        actions.put("Start", Mockito.mock(Action.class));

        JFrame frame = new JFrame();
        ButtonPanel panel = new ButtonPanel(actions, frame);
        assertThat(panel.getComponents()).hasSize(1);
    }

    @Test
    void buttonAction()
    {
        Map<String, Action> actions = new HashMap<>();
        Action action = Mockito.mock(Action.class);
        actions.put("Start", action);

        JFrame frame = new JFrame();
        ButtonPanel panel = new ButtonPanel(actions, frame);
        assertThat(panel.getComponent(0)).isInstanceOf(JButton.class);
        JButton button = (JButton) panel.getComponent(0);
        button.doClick();
        verify(action).doAction();
    }
}
