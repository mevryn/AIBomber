package pl.dszi.engine.constant;

public enum Resource {
    CRATEPATH("/images/crate.png"),
    BOMBPATH("/images/bomba.png"),
    CHARACTERPATH("/images/BOMBI.png"),
    EXPLOSIONPATH("/images/explosion.png"),
    BOOSTERPATH("/images/booster.png"),
    CRATEBONUSPATH("/images/crateBonus.png"),
    WALLPATH("/images/wall.png"),
    LAUNCHERPATH("/images/launcher.gif");

    private String path;

    public String getPath() {
        return path;
    }

    Resource(String path) {
        this.path = path;
    }
}
