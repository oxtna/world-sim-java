package simulation.organisms;

import simulation.SpriteManager;
import simulation.Vector2;
import simulation.World;

public final class Turtle extends Animal {
    private static final int DEFAULT_POWER = 2;
    private static final int DEFAULT_INITIATIVE = 1;

    public Turtle(Vector2 position, World world) {
        super(DEFAULT_POWER, DEFAULT_INITIATIVE, position, SpriteManager.getInstance().getSprite("turtle"), world);
    }

    public Turtle(int age, int power, int initiative, Vector2 position, World world) {
        super(power, initiative, position, SpriteManager.getInstance().getSprite("turtle"), world);
        this.age = age;
    }

    public static String name() {
        return "Zolw";
    }

    @Override
    public boolean canDeflect(Organism other) {
        return other.power < 5;
    }

    @Override
    protected Animal birth(Vector2 position) {
        return new Turtle(position, world);
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }
}
