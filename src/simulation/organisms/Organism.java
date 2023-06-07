package simulation.organisms;

import simulation.Vector2;
import simulation.World;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public abstract class Organism {
    protected boolean dead;
    protected boolean acted;
    protected int age;
    protected int power;
    protected int initiative;
    protected Vector2 position;
    protected String sprite;
    protected World world;

    public Organism(int power, int initiative, Vector2 position, String sprite, World world) {
        this.dead = false;
        this.acted = false;
        this.age = 0;
        this.power = power;
        this.initiative = initiative;
        this.position = position;
        this.sprite = sprite;
        this.world = world;
    }

    public static Organism load(Scanner reader, World world) {
        String line = reader.nextLine();
        String[] words = line.split(" ");
        if (words.length != 6 && words.length != 9) {
            throw new RuntimeException("Invalid save file");
        }
        // human objects have 9 words
        if (words.length == 9) {
            return new Human(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), new Vector2(Integer.parseInt(words[4]), Integer.parseInt(words[5])), Boolean.parseBoolean(words[6]), Integer.parseInt(words[7]), Integer.parseInt(words[8]), world);
        } else {
            if (Objects.equals(words[0], Antelope.name())) {
                return new Antelope(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), new Vector2(Integer.parseInt(words[4]), Integer.parseInt(words[5])), world);
            } else if (Objects.equals(words[0], Belladonna.name())) {
                return new Belladonna(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), new Vector2(Integer.parseInt(words[4]), Integer.parseInt(words[5])), world);
            } else if (Objects.equals(words[0], Dandelion.name())) {
                return new Dandelion(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), new Vector2(Integer.parseInt(words[4]), Integer.parseInt(words[5])), world);
            } else if (Objects.equals(words[0], Fox.name())) {
                return new Fox(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), new Vector2(Integer.parseInt(words[4]), Integer.parseInt(words[5])), world);
            } else if (Objects.equals(words[0], Grass.name())) {
                return new Grass(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), new Vector2(Integer.parseInt(words[4]), Integer.parseInt(words[5])), world);
            } else if (Objects.equals(words[0], Guarana.name())) {
                return new Guarana(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), new Vector2(Integer.parseInt(words[4]), Integer.parseInt(words[5])), world);
            } else if (Objects.equals(words[0], Heracleum.name())) {
                return new Heracleum(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), new Vector2(Integer.parseInt(words[4]), Integer.parseInt(words[5])), world);
            } else if (Objects.equals(words[0], Sheep.name())) {
                return new Sheep(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), new Vector2(Integer.parseInt(words[4]), Integer.parseInt(words[5])), world);
            } else if (Objects.equals(words[0], Turtle.name())) {
                return new Turtle(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), new Vector2(Integer.parseInt(words[4]), Integer.parseInt(words[5])), world);
            } else if (Objects.equals(words[0], Wolf.name())) {
                return new Wolf(Integer.parseInt(words[1]), Integer.parseInt(words[2]), Integer.parseInt(words[3]), new Vector2(Integer.parseInt(words[4]), Integer.parseInt(words[5])), world);
            } else {
                throw new RuntimeException("Invalid save file");
            }
        }
    }

    public int getAge() {
        return age;
    }

    public int getInitiative() {
        return initiative;
    }

    public Vector2 getPosition() {
        return position;
    }

    public abstract void act();

    /**
     * @param other organism that moved to this organism's position
     * @return true if the other succeeds, otherwise false
     */
    public abstract boolean collide(Organism other);

    public abstract String getSprite();

    protected abstract Organism birth(Vector2 position) throws AnimalNotReproducibleException;

    public void die() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isAnimal() {
        return false;
    }

    public void getOlder() {
        age++;
    }

    public abstract String getName();

    public void save(FileWriter fileWriter) {
        try {
            fileWriter.write(getName());
            fileWriter.write(" ");
            fileWriter.write(Integer.toString(age));
            fileWriter.write(" ");
            fileWriter.write(Integer.toString(power));
            fileWriter.write(" ");
            fileWriter.write(Integer.toString(initiative));
            fileWriter.write(" ");
            fileWriter.write(Integer.toString(position.x()));
            fileWriter.write(" ");
            fileWriter.write(Integer.toString(position.y()));
            fileWriter.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
