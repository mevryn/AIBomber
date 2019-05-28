package pl.dszi.engine.constant;

public enum Resource {
    CRATEPATH("/images/crate.png"),
    BOMBPATH("/images/bomba.png"),
    CHARACTERPATH("/images/BOMBI.png"),
    EXPLOSIONPATH("/images/explosion.png"),
    WALLPATH("/images/wall.png");

    private String path;

    public String getPath() {
        return path;
    }

    Resource(String path) {
        this.path = path;
    }
}
