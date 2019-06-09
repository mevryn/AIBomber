package pl.dszi.player.noob;

import pl.dszi.board.Direction;

public class State {

    private Node place;
    private Direction direction;

    public State(Node place, Direction direction) {
        this.place = place;
        this.direction = direction;
    }

    public Node getPlace() {
        return place;
    }

    public void setPlace(Node place) {
        this.place = place;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    int getF() {
        return place.getF();
    }
}
