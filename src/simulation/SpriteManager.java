package simulation;

import java.util.HashMap;
import java.util.Map;

public class SpriteManager {
    private static SpriteManager instance;
    private final Map<String, String> sprites;

    private SpriteManager() {
        this.sprites = new HashMap<>();
        this.sprites.put("empty", "");
        this.sprites.put("antelope", "\uD83E\uDD8C");
        this.sprites.put("belladonna", "\uD83D\uDC80\uD83C\uDF47");
        this.sprites.put("dandelion", "\uD83C\uDF3B");
        this.sprites.put("fox", "\uD83E\uDD8A");
        this.sprites.put("grass", "\uD83C\uDF31");
        this.sprites.put("guarana", "\uD83C\uDF53");
        this.sprites.put("heracleum", "\uD83D\uDC80\uD83C\uDF3F");
        this.sprites.put("human", "\uD83D\uDC83");
        this.sprites.put("sheep", "\uD83D\uDC11");
        this.sprites.put("turtle", "\uD83D\uDC22");
        this.sprites.put("wolf", "\uD83D\uDC3A");
    }

    public static SpriteManager getInstance() {
        if (instance == null) {
            instance = new SpriteManager();
        }
        return instance;
    }

    public String getSprite(String name) {
        return this.sprites.getOrDefault(name, null);
    }
}
