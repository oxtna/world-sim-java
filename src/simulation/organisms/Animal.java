package simulation.organisms;

import simulation.RNG;
import simulation.Vector2;
import simulation.World;
import simulation.maps.MapSlotEmptyException;

import java.util.List;

public abstract class Animal extends Organism {
    public Animal(int power, int initiative, Vector2 position, String sprite, World world) {
        super(power, initiative, position, sprite, world);
    }

    @Override
    public void act() {
        List<Vector2> adjacent = world.getMap().getAdjacentPositions(position);
        Vector2 nextPosition = adjacent.get(RNG.nextInt(adjacent.size()));
        moveAndCollide(nextPosition);
    }

    @Override
    public boolean collide(Organism other) {
        if (other.getClass().equals(this.getClass())) {
            List<Vector2> adjacent = world.getMap().getAdjacentPositions(position);
            adjacent.removeIf(pos -> !world.getMap().isEmpty(pos));
            // if an empty spot cannot be found, do not birth a child
            if (adjacent.isEmpty()) {
                return false;
            }
            Vector2 childPosition = adjacent.get(RNG.nextInt(adjacent.size()));
            try {
                Organism child = birth(childPosition);
                world.addOrganism(child, childPosition);
                other.acted = true;
                world.log(getName() + " urodzil sie na pozycji " + childPosition);
            } catch (AnimalNotReproducibleException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
        if (canDeflect(other)) {
            other.acted = true;
            world.log(getName() + " odbil " + other.getName() + " na pozycji " + position);
            return false;
        }
        if (power > other.power) {
            other.die();
            other.acted = true;
            world.log(other.getName() + " zginal atakujac " + getName() + " na pozycji " + position);
            return false;
        } else {
            die();
            other.acted = true;
            world.log(other.getName() + " zabil " + getName() + " na pozycji " + position);
            return true;
        }
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    public boolean canDeflect(Organism other) {
        return false;
    }

    public void moveAndCollide(Vector2 destination) {
        if (world.getMap().isEmpty(destination)) {
            try {
                world.getMap().moveOrganism(position, destination);
                position = destination;
                world.log(getName() + " poszedl na pozycje " + position);
            } catch (MapSlotEmptyException e) {
                throw new RuntimeException(e);
            }
        } else {
            Organism defender = world.getMap().getOrganism(destination);
            if (defender.collide(this)) {
                try {
                    world.getMap().moveOrganism(position, destination);
                    position = destination;
                } catch (MapSlotEmptyException e) {
                    throw new RuntimeException(e);
                }
                // if a plant killed an animal, let it move to the plant's position before dying
                if (isDead()) {
                    try {
                        world.getMap().removeOrganism(position);
                    } catch (MapSlotEmptyException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                try {
                    if (this.dead) {
                        world.getMap().removeOrganism(position);
                    }
                } catch (MapSlotEmptyException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
