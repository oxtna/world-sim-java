package simulation.organisms;

import simulation.SpriteManager;
import simulation.Vector2;
import simulation.World;

public final class Wolf extends Animal {
    private static final int DEFAULT_POWER = 9;
    private static final int DEFAULT_INITIATIVE = 5;

    public Wolf(Vector2 position, World world) {
        super(DEFAULT_POWER, DEFAULT_INITIATIVE, position, SpriteManager.getInstance().getSprite("wolf"), world);
    }

    public Wolf(int age, int power, int initiative, Vector2 position, World world) {
        super(power, initiative, position, SpriteManager.getInstance().getSprite("wolf"), world);
        this.age = age;
    }

    public static String name() {
        return "Wilk";
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

    @Override
    protected Animal birth(Vector2 position) {
        return new Wolf(position, world);
    }

    @Override
    public String getName() {
        return name();
    }
}
