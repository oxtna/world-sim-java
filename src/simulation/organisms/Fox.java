package simulation.organisms;

import simulation.RNG;
import simulation.SpriteManager;
import simulation.Vector2;
import simulation.World;

import java.util.List;

public final class Fox extends Animal {
    private static final int DEFAULT_POWER = 3;
    private static final int DEFAULT_INITIATIVE = 7;

    public Fox(Vector2 position, World world) {
        super(DEFAULT_POWER, DEFAULT_INITIATIVE, position, SpriteManager.getInstance().getSprite("fox"), world);
    }

    public Fox(int age, int power, int initiative, Vector2 position, World world) {
        super(power, initiative, position, SpriteManager.getInstance().getSprite("fox"), world);
        this.age = age;
    }

    public static String name() {
        return "Lis";
    }

    @Override
    public void act() {
        List<Vector2> adjacent = world.getMap().getAdjacentPositions(position);
        adjacent.removeIf(vec -> {
            Organism other = world.getMap().getOrganism(vec);
            return other != null && other.power > this.power;
        });
        Vector2 nextPosition = adjacent.get(RNG.nextInt(adjacent.size()));
        moveAndCollide(nextPosition);
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

    @Override
    protected Animal birth(Vector2 position) {
        return new Fox(position, world);
    }

    @Override
    public String getName() {
        return name();
    }
}
