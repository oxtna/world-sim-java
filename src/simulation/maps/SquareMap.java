package simulation.maps;

import gui.OrganismCreationCallback;
import simulation.SpriteManager;
import simulation.Vector2;
import simulation.organisms.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SquareMap implements Map {
    private final List<Organism> slots;
    private final List<JButton> buttons;
    private final int width;
    private final int height;

    public SquareMap(int width, int height, List<JButton> buttons) {
        this.slots = new ArrayList<>(width * height);
        for (int i = 0; i < width * height; i++) {
            this.slots.add(null);
        }
        this.buttons = buttons;
        this.width = width;
        this.height = height;
    }

    @Override
    public Vector2 getSize() {
        return new Vector2(width, height);
    }

    @Override
    public List<Vector2> getAdjacentPositions(Vector2 position) {
        List<Vector2> adjacent = new ArrayList<>(4);
        if (position.x() < 0 || position.x() >= width || position.y() < 0 || position.y() >= height) {
            throw new IndexOutOfBoundsException();
        }
        if (position.x() != 0) {
            adjacent.add(new Vector2(position.x() - 1, position.y()));
        }
        if (position.x() != width - 1) {
            adjacent.add(new Vector2(position.x() + 1, position.y()));
        }
        if (position.y() != 0) {
            adjacent.add(new Vector2(position.x(), position.y() - 1));
        }
        if (position.y() != height - 1) {
            adjacent.add(new Vector2(position.x(), position.y() + 1));
        }
        return adjacent;
    }

    @Override
    public List<Vector2> getDiagonalPositions(Vector2 position) {
        List<Vector2> adjacent = new ArrayList<>(4);
        if (position.x() < 0 || position.x() >= width || position.y() < 0 || position.y() >= height) {
            throw new IndexOutOfBoundsException();
        }
        if (position.x() != 0 && position.y() != 0) {
            adjacent.add(new Vector2(position.x() - 1, position.y() - 1));
        }
        if (position.x() != width - 1 && position.y() != 0) {
            adjacent.add(new Vector2(position.x() + 1, position.y() - 1));
        }
        if (position.x() != 0 && position.y() != height - 1) {
            adjacent.add(new Vector2(position.x() - 1, position.y() + 1));
        }
        if (position.x() != width - 1 && position.y() != height - 1) {
            adjacent.add(new Vector2(position.x() + 1, position.y() + 1));
        }
        return adjacent;
    }

    @Override
    public boolean isEmpty(Vector2 position) {
        return slots.get(convertPositionToIndex(position)) == null;
    }

    @Override
    public void moveOrganism(Vector2 source, Vector2 destination) throws MapSlotEmptyException {
        if (slots.get(convertPositionToIndex(source)) == null) {
            throw new MapSlotEmptyException();
        }
        slots.set(convertPositionToIndex(destination), slots.get(convertPositionToIndex(source)));
        slots.set(convertPositionToIndex(source), null);
    }

    @Override
    public Organism getOrganism(Vector2 position) {
        return slots.get(convertPositionToIndex(position));
    }

    @Override
    public void removeOrganism(Vector2 position) throws MapSlotEmptyException {
        if (slots.get(convertPositionToIndex(position)) == null) {
            throw new MapSlotEmptyException();
        }
        slots.set(convertPositionToIndex(position), null);
    }

    @Override
    public void addOrganism(Vector2 position, Organism organism) throws MapSlotNotEmptyException {
        if (slots.get(convertPositionToIndex(position)) != null) {
            throw new MapSlotNotEmptyException();
        }
        slots.set(convertPositionToIndex(position), organism);
    }

    @Override
    public void clear() {
        slots.clear();
        for (int i = 0; i < width * height; i++) {
            slots.add(null);
        }
    }

    @Override
    public void render(OrganismCreationCallback callback) {
        for (int i = 0; i < this.slots.size(); i++) {
            JButton button = buttons.get(i);
            if (slots.get(i) == null) {
                button.setText(SpriteManager.getInstance().getSprite("empty"));
                int finalI = i;
                button.addActionListener(e -> {
                    String[] options = {Antelope.name(), Belladonna.name(), Dandelion.name(), Fox.name(), Grass.name(), Guarana.name(), Heracleum.name(), Sheep.name(), Turtle.name(), Wolf.name()};
                    callback.create((String) JOptionPane.showInputDialog(null, "Pick organism type", "", JOptionPane.PLAIN_MESSAGE, null, options, null), new Vector2(finalI % width, finalI / width));
                    for (ActionListener listener : button.getActionListeners()) {
                        button.removeActionListener(listener);
                    }
                    button.setText(slots.get(finalI).getSprite());
                });
            } else {
                button.setText(slots.get(i).getSprite());
                for (ActionListener listener : button.getActionListeners()) {
                    button.removeActionListener(listener);
                }
            }
        }
    }

    private int convertPositionToIndex(Vector2 position) {
        return position.y() * width + position.x();
    }
}
