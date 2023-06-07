package simulation.organisms;

import simulation.SpriteManager;
import simulation.Vector2;
import simulation.World;

public final class Antelope extends Animal {
    private static final int DEFAULT_POWER = 4;
    private static final int DEFAULT_INITIATIVE = 4;

    public Antelope(Vector2 position, World world) {
        super(DEFAULT_POWER, DEFAULT_INITIATIVE, position, SpriteManager.getInstance().getSprite("antelope"), world);
    }

    public Antelope(int age, int power, int initiative, Vector2 position, World world) {
        super(power, initiative, position, SpriteManager.getInstance().getSprite("antelope"), world);
        this.age = age;
    }

    public static String name() {
        return "Antylopa";
    }

    @Override
    public void act() {
        super.act();
        if (!this.dead && !this.acted) {
            super.act();
        }
        this.acted = false;
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

    @Override
    protected Animal birth(Vector2 position) {
        return new Antelope(position, world);
    }

    @Override
    public String getName() {
        return name();
    }
}
