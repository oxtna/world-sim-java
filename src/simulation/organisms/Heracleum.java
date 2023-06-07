package simulation.organisms;

import simulation.SpriteManager;
import simulation.Vector2;
import simulation.World;
import simulation.maps.MapSlotEmptyException;

import java.util.List;

public final class Heracleum extends Plant {
    private static final int DEFAULT_POWER = 10;

    public Heracleum(Vector2 position, World world) {
        super(DEFAULT_POWER, DEFAULT_INITIATIVE, position, SpriteManager.getInstance().getSprite("heracleum"), world);
    }

    public Heracleum(int age, int power, int initiative, Vector2 position, World world) {
        super(power, initiative, position, SpriteManager.getInstance().getSprite("heracleum"), world);
        this.age = age;
    }

    public static String name() {
        return "BarszczSosnowskiego";
    }

    @Override
    public void act() {
        List<Vector2> adjacent = world.getMap().getAdjacentPositions(position);
        List<Vector2> diagonal = world.getMap().getDiagonalPositions(position);
        for (Vector2 v : diagonal) {
            adjacent.add(v);
        }
        for (Vector2 adjacentPosition : adjacent) {
            Organism adjacentOrganism = world.getMap().getOrganism(adjacentPosition);
            if (adjacentOrganism == null) {
                continue;
            }
            if (adjacentOrganism.isAnimal()) {
                try {
                    adjacentOrganism.die();
                    world.getMap().removeOrganism(adjacentPosition);
                    world.log(adjacentOrganism.getName() + " umarl przez " + getName());
                } catch (MapSlotEmptyException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        super.act();
    }

    @Override
    public boolean collide(Organism other) {
        die();
        other.die();
        world.log(other.getName() + " zjadl " + getName() + " na pozycji " + position);
        world.log(other.getName() + " umarl");
        return true;
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

    @Override
    protected Plant birth(Vector2 position) {
        return new Heracleum(position, world);
    }

    @Override
    public String getName() {
        return name();
    }
}
