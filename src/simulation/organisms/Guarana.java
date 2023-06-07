package simulation.organisms;

import simulation.SpriteManager;
import simulation.Vector2;
import simulation.World;

public final class Guarana extends Plant {
    private static final int DEFAULT_POWER = 0;

    public Guarana(Vector2 position, World world) {
        super(DEFAULT_POWER, DEFAULT_INITIATIVE, position, SpriteManager.getInstance().getSprite("guarana"), world);
    }

    public Guarana(int age, int power, int initiative, Vector2 position, World world) {
        super(power, initiative, position, SpriteManager.getInstance().getSprite("guarana"), world);
        this.age = age;
    }

    public static String name() {
        return "Guarana";
    }

    @Override
    public boolean collide(Organism other) {
        other.power += 3;
        return super.collide(other);
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

    @Override
    protected Plant birth(Vector2 position) {
        return new Guarana(position, world);
    }

    @Override
    public String getName() {
        return name();
    }
}
