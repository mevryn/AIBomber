package pl.dszi.player.noob;

class Distances {
    private Node Node1;
    private Node Node2;

    public Distances(Node Node1, Node Node2) {
        this.Node1 = Node1;
        this.Node2 = Node2;
    }

    protected int returnManhattanDistance(){
        return Math.abs(Node1.getCell().getIndexX()-Node2.getCell().getIndexX())+Math.abs(Node1.getCell().getIndexY()-Node2.getCell().getIndexY());
    }
}
