package simulation;

import simulation.maps.Map;
import simulation.maps.MapSlotNotEmptyException;
import simulation.maps.SquareMap;
import simulation.organisms.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class World {
    private final JTextArea logger;
    private final Map map;
    private List<Organism> organisms;
    private List<Organism> children;
    private Human human;
    private boolean ended;

    public World(int width, int height, List<JButton> buttons, JTextArea logger) {
        this.logger = logger;
        this.organisms = new ArrayList<>();
        this.children = new ArrayList<>();
        this.map = new SquareMap(width, height, buttons);
        this.ended = false;
        Vector2 humanPosition = new Vector2(RNG.nextInt(width), RNG.nextInt(height));
        this.human = new Human(humanPosition, this);
        this.addOrganism(this.human, humanPosition);
        int area = width * height;
        if (area < 2) {
            return;
        }
        int organismCount = (int) Math.sqrt(area) + RNG.nextInt((int) Math.sqrt(area));
        for (int i = 0; i < organismCount; i++) {
            int type = RNG.nextInt(10);
            Vector2 position = new Vector2(RNG.nextInt(width), RNG.nextInt(height));
            while (!this.map.isEmpty(position)) {
                position = new Vector2(RNG.nextInt(width), RNG.nextInt(height));
            }
            switch (type) {
                case 0 -> this.addOrganism(new Turtle(position, this), position);
                case 1 -> this.addOrganism(new Grass(position, this), position);
                case 2 -> this.addOrganism(new Antelope(position, this), position);
                case 3 -> this.addOrganism(new Belladonna(position, this), position);
                case 4 -> this.addOrganism(new Dandelion(position, this), position);
                case 5 -> this.addOrganism(new Wolf(position, this), position);
                case 6 -> this.addOrganism(new Fox(position, this), position);
                case 7 -> this.addOrganism(new Guarana(position, this), position);
                case 8 -> this.addOrganism(new Heracleum(position, this), position);
                case 9 -> this.addOrganism(new Sheep(position, this), position);
            }
        }
    }

    public Map getMap() {
        return map;
    }

    public void addOrganism(Organism organism, Vector2 position) {
        try {
            children.add(organism);
            map.addOrganism(position, organism);
        } catch (MapSlotNotEmptyException e) {
            throw new RuntimeException(e);
        }
    }

    public void takeTurn() {
        organisms.sort(Comparator.comparingInt(Organism::getInitiative).thenComparingInt(Organism::getAge));
        for (Organism organism : organisms) {
            if (!organism.isDead()) {
                organism.act();
            }
        }
        organisms.addAll(children);
        children.clear();
        organisms.removeIf(Organism::isDead);
        for (Organism organism : organisms) {
            organism.getOlder();
        }
    }

    public void end() {
        ended = true;
        log("Koniec rozgrywki z powodu smierci czlowieka");
    }

    public void render() {
        map.render((name, position) -> {
            if (Objects.equals(name, Antelope.name())) {
                this.addOrganism(new Antelope(position, this), position);
            } else if (Objects.equals(name, Belladonna.name())) {
                this.addOrganism(new Belladonna(position, this), position);
            } else if (Objects.equals(name, Dandelion.name())) {
                this.addOrganism(new Dandelion(position, this), position);
            } else if (Objects.equals(name, Fox.name())) {
                this.addOrganism(new Fox(position, this), position);
            } else if (Objects.equals(name, Grass.name())) {
                this.addOrganism(new Grass(position, this), position);
            } else if (Objects.equals(name, Guarana.name())) {
                this.addOrganism(new Guarana(position, this), position);
            } else if (Objects.equals(name, Heracleum.name())) {
                this.addOrganism(new Heracleum(position, this), position);
            } else if (Objects.equals(name, Sheep.name())) {
                this.addOrganism(new Sheep(position, this), position);
            } else if (Objects.equals(name, Turtle.name())) {
                this.addOrganism(new Turtle(position, this), position);
            } else if (Objects.equals(name, Wolf.name())) {
                this.addOrganism(new Wolf(position, this), position);
            }
        });
    }

    public void passDirection(InputDirection direction) {
        human.handleDirection(direction);
    }

    public void log(String message) {
        this.logger.append(message + "\n");
    }

    public void save(String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(Integer.toString(this.map.getSize().x()));
            fileWriter.write(" ");
            fileWriter.write(Integer.toString(this.map.getSize().y()));
            fileWriter.write("\n");
            for (Organism organism : organisms) {
                organism.save(fileWriter);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(String filename) {
        try (Scanner reader = new Scanner(new File(filename))) {
            this.map.clear();
            String line = reader.nextLine();
            this.organisms = new ArrayList<>();
            this.children = new ArrayList<>();
            this.ended = false;
            this.logger.setText("");
            while (reader.hasNextLine()) {
                Organism organism = Organism.load(reader, this);
                if (organism instanceof Human) {
                    this.human = (Human) organism;
                }
                this.addOrganism(organism, organism.getPosition());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void triggerSpecialAbility() {
        human.triggerSpecialAbility();
    }
}
