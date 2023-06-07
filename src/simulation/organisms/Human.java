package simulation.organisms;

import simulation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public final class Human extends Animal {
    private static final int DEFAULT_POWER = 5;
    private static final int DEFAULT_INITIATIVE = 4;
    private InputDirection direction;
    private boolean abilityActive;
    private int duration;
    private int cooldown;

    public Human(Vector2 position, World world) {
        super(DEFAULT_POWER, DEFAULT_INITIATIVE, position, SpriteManager.getInstance().getSprite("human"), world);
        this.direction = InputDirection.NONE;
        this.abilityActive = false;
        this.duration = 0;
        this.cooldown = 0;
    }

    public Human(int age, int power, int initiative, Vector2 position, boolean abilityActive, int duration, int cooldown, World world) {
        super(power, initiative, position, SpriteManager.getInstance().getSprite("human"), world);
        this.age = age;
        this.abilityActive = abilityActive;
        this.duration = duration;
        this.cooldown = cooldown;
        this.direction = InputDirection.NONE;
    }

    @Override
    public void act() {
        if (duration == 0) {
            abilityActive = false;
        }
        Vector2 directionVector = switch (direction) {
            case NONE -> new Vector2(0, 0);
            case LEFT -> new Vector2(-1, 0);
            case RIGHT -> new Vector2(1, 0);
            case UP -> new Vector2(0, -1);
            case DOWN -> new Vector2(0, 1);
        };
        direction = InputDirection.NONE;
        moveAndCollideIfPossible(directionVector);
        if (!this.dead && abilityActive) {
            if (duration > 2) {
                moveAndCollideIfPossible(directionVector);
            } else if (RNG.nextInt(2) == 0) {
                moveAndCollideIfPossible(directionVector);
            }
        }
        if (abilityActive) {
            duration--;
        } else if (cooldown > 0) {
            cooldown--;
        }
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

    @Override
    protected Animal birth(Vector2 position) throws AnimalNotReproducibleException {
        throw new AnimalNotReproducibleException("Humans can not reproduce");
    }

    @Override
    public void die() {
        super.die();
        world.end();
    }

    @Override
    public String getName() {
        return "Czlowiek";
    }

    public void handleDirection(InputDirection direction) {
        this.direction = direction;
    }

    public void triggerSpecialAbility() {
        if (!abilityActive && cooldown > 0) {
            world.log("Umiejetnosc w trakcie odnawiania: " + this.cooldown + " tur");
        } else if (!abilityActive && duration <= 0) {
            world.log("Aktywowano umiejetnosc na 5 tur");
            abilityActive = true;
            cooldown = 5;
            duration = 5;
        } else {
            world.log("Umiejetnosc aktywna na " + this.duration + " tur");
        }
    }

    @Override
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
            fileWriter.write(" ");
            fileWriter.write(String.valueOf(abilityActive));
            fileWriter.write(" ");
            fileWriter.write(Integer.toString(duration));
            fileWriter.write(" ");
            fileWriter.write(Integer.toString(cooldown));
            fileWriter.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void moveAndCollideIfPossible(Vector2 directionVector) {
        Vector2 nextPosition = this.position.add(directionVector);
        List<Vector2> adjacent = world.getMap().getAdjacentPositions(this.position);
        for (Vector2 adjacentPosition : adjacent) {
            if (adjacentPosition.x() == nextPosition.x() && adjacentPosition.y() == nextPosition.y()) {
                moveAndCollide(nextPosition);
            }
        }
    }
}
