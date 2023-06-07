package simulation.organisms;

import simulation.SpriteManager;
import simulation.Vector2;
import simulation.World;

public final class Dandelion extends Plant {
    private static final int DEFAULT_POWER = 0;

    public Dandelion(Vector2 position, World world) {
        super(DEFAULT_POWER, DEFAULT_INITIATIVE, position, SpriteManager.getInstance().getSprite("dandelion"), world);
    }

    public Dandelion(int age, int power, int initiative, Vector2 position, World world) {
        super(power, initiative, position, SpriteManager.getInstance().getSprite("dandelion"), world);
        this.age = age;
    }

    public static String name() {
        return "Mlecz";
    }

    @Override
    public void act() {
        for (int i = 0; i < 3; i++) {
            if (this.acted) {
                break;
            }
            super.act();
        }
        this.acted = false;
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

    @Override
    protected Plant birth(Vector2 position) {
        return new Dandelion(position, world);
    }

    @Override
    public String getName() {
        return name();
    }
}
