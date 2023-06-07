package simulation;

public record Vector2(int x, int y) {
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }
}
