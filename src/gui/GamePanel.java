package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {
    public GamePanel(List<JButton> buttons, int width, int height) {
        for (JButton button : buttons) {
            this.add(button);
        }
        this.setPreferredSize(new Dimension(width * 60, height * 40));
        this.setBackground(Color.WHITE);
        this.setLayout(new GridLayout(width, height));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
