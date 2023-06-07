package simulation.organisms;

import simulation.SpriteManager;
import simulation.Vector2;
import simulation.World;

public final class Grass extends Plant {
    private static final int DEFAULT_POWER = 0;

    public Grass(Vector2 position, World world) {
        super(DEFAULT_POWER, DEFAULT_INITIATIVE, position, SpriteManager.getInstance().getSprite("grass"), world);
    }

    public Grass(int age, int power, int initiative, Vector2 position, World world) {
        super(power, initiative, position, SpriteManager.getInstance().getSprite("grass"), world);
        this.age = age;
    }

    public static String name() {
        return "Trawa";
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

    @Override
    protected Plant birth(Vector2 position) {
        return new Grass(position, world);
    }

    @Override
    public String getName() {
        return name();
    }
}
