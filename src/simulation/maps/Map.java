package simulation.maps;

import gui.OrganismCreationCallback;
import simulation.Vector2;
import simulation.organisms.Organism;

import java.util.List;

public interface Map {
    Vector2 getSize();
    List<Vector2> getAdjacentPositions(Vector2 position);
    List<Vector2> getDiagonalPositions(Vector2 position);

    boolean isEmpty(Vector2 position);

    void moveOrganism(Vector2 source, Vector2 destination) throws MapSlotEmptyException;

    Organism getOrganism(Vector2 position);

    void removeOrganism(Vector2 position) throws MapSlotEmptyException;

    void addOrganism(Vector2 position, Organism organism) throws MapSlotNotEmptyException;

    void clear();

    void render(OrganismCreationCallback callback);
}
