package gui;

import simulation.InputDirection;
import simulation.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame implements ActionListener {
    private final World world;
    private final JMenuItem load, save;

    public Window() {
        int width = Integer.parseInt((String) JOptionPane.showInputDialog(this, "Input world width", "", JOptionPane.PLAIN_MESSAGE, null, null, "20"));
        int height = Integer.parseInt((String) JOptionPane.showInputDialog(this, "Input world height", "", JOptionPane.PLAIN_MESSAGE, null, null, "20"));
        List<JButton> buttons = new ArrayList<>(width * height);
        for (int i = 0; i < width * height; i++) {
            JButton button = new JButton("");
            button.setFocusable(false);
            button.setFont(new Font(button.getFont().getName(), Font.PLAIN, 12));
            buttons.add(button);
        }
        JTextArea textArea = new JTextArea(40, 40);
        textArea.setFont(textArea.getFont().deriveFont(16.0f));
        JPanel main = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        main.add(new GamePanel(buttons, width, height));
        main.add(new LogPanel(textArea));
        this.world = new World(width, height, buttons, textArea);
        this.world.render();
        this.add(main);
        this.addKeyListener(new GameKeyAdapter());
        this.setTitle("Symulacja swiata (Java) - Rafał Kajomof 193322");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        save = new JMenuItem("Save");
        save.addActionListener(this);
        load = new JMenuItem("Load");
        load.addActionListener(this);
        JMenu menu = new JMenu();
        menu.add(save);
        menu.add(load);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            String filename = filenameDialog("Save file");
            world.save(filename);
        } else if (e.getSource() == load) {
            String filename = filenameDialog("Load file");
            world.load(filename);
            world.render();
        }
    }

    public String filenameDialog(String title) {
        return (String) JOptionPane.showInputDialog(this, "Input the filename", title, JOptionPane.PLAIN_MESSAGE, null, null, null);
    }

    public class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    world.passDirection(InputDirection.LEFT);
                    world.takeTurn();
                    world.render();
                }
                case KeyEvent.VK_RIGHT -> {
                    world.passDirection(InputDirection.RIGHT);
                    world.takeTurn();
                    world.render();
                }
                case KeyEvent.VK_UP -> {
                    world.passDirection(InputDirection.UP);
                    world.takeTurn();
                    world.render();
                }
                case KeyEvent.VK_DOWN -> {
                    world.passDirection(InputDirection.DOWN);
                    world.takeTurn();
                    world.render();
                }
                case KeyEvent.VK_ENTER -> {
                    world.takeTurn();
                    world.render();
                }
                case KeyEvent.VK_SPACE -> world.triggerSpecialAbility();
                case KeyEvent.VK_S -> {
                    String filename = filenameDialog("Save file");
                    world.save(filename);
                }
                case KeyEvent.VK_L -> {
                    String filename = filenameDialog("Load file");
                    world.load(filename);
                    world.render();
                }
            }
        }
    }
}
