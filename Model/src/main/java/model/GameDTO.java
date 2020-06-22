package model;

import java.io.Serializable;
import java.util.List;

public class GameDTO implements Serializable {
    private Game game;
    private List<Integer> positions;

    public GameDTO(Game game, List<Integer> positions) {
        this.game = game;
        this.positions = positions;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }
}
