package simulation.organisms;

import simulation.RNG;
import simulation.Vector2;
import simulation.World;

import java.util.List;

public abstract class Plant extends Organism {
    protected static final int DEFAULT_INITIATIVE = 0;

    public Plant(int power, int initiative, Vector2 position, String sprite, World world) {
        super(power, initiative, position, sprite, world);
    }

    @Override
    public void act() {
        List<Vector2> adjacent = world.getMap().getAdjacentPositions(position);
        adjacent.removeIf(pos -> !world.getMap().isEmpty(pos));
        if (adjacent.isEmpty()) {
            return;
        }
        // plants have a 10% chance to spread to an adjacent position
        boolean willSpread = RNG.nextInt(10) == 0;
        if (willSpread) {
            Vector2 childPosition = adjacent.get(RNG.nextInt(adjacent.size()));
            Plant child = birth(childPosition);
            this.acted = true;
            world.addOrganism(child, childPosition);
            world.log(getName() + " urosl na pozycji " + childPosition);
        }
    }

    @Override
    public boolean collide(Organism other) {
        die();
        world.log(other.getName() + " zjadl " + getName() + " na pozycji " + position);
        return true;
    }

    protected abstract Plant birth(Vector2 position);
}
