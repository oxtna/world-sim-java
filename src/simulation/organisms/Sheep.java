package simulation.organisms;

import simulation.SpriteManager;
import simulation.Vector2;
import simulation.World;

public final class Sheep extends Animal {
    private static final int DEFAULT_POWER = 4;
    private static final int DEFAULT_INITIATIVE = 4;

    public Sheep(Vector2 position, World world) {
        super(DEFAULT_POWER, DEFAULT_INITIATIVE, position, SpriteManager.getInstance().getSprite("sheep"), world);
    }

    public Sheep(int age, int power, int initiative, Vector2 position, World world) {
        super(power, initiative, position, SpriteManager.getInstance().getSprite("sheep"), world);
        this.age = age;
    }

    public static String name() {
        return "Owca";
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

    @Override
    protected Animal birth(Vector2 position) {
        return new Sheep(position, world);
    }

    @Override
    public String getName() {
        return name();
    }
}
