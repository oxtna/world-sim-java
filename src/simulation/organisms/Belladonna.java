package simulation.organisms;

import simulation.SpriteManager;
import simulation.Vector2;
import simulation.World;

public final class Belladonna extends Plant {
    private static final int DEFAULT_POWER = 99;

    public Belladonna(Vector2 position, World world) {
        super(DEFAULT_POWER, DEFAULT_INITIATIVE, position, SpriteManager.getInstance().getSprite("belladonna"), world);
    }

    public Belladonna(int age, int power, int initiative, Vector2 position, World world) {
        super(power, initiative, position, SpriteManager.getInstance().getSprite("belladonna"), world);
        this.age = age;
    }

    public static String name() {
        return "WilczeJagody";
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
        return new Belladonna(position, world);
    }

    @Override
    public String getName() {
        return name();
    }
}
