package pl.dszi.engine;

public class Constants {
    public static final int DEFAULT_CELL_SIZE = 64;
    public static final int DEFAULT_OFFSET = 64;
    public static final int DEFAULT_GAME_TILES_HORIZONTALLY = 13;
    public static final int DEFAULT_GAME_TILES_VERTICALLY = 11;
    public static final int DEFAULT_BORDER = 2;
    public static final int HALF_DEFAULT_CELL_SIZE = DEFAULT_CELL_SIZE / 2;
    public static final int HALF_DEFAULT_CELL_SIZE_RENDER = DEFAULT_CELL_SIZE / 4;
    public static final int DEFAULT_GAME_WIDTH = DEFAULT_CELL_SIZE * (DEFAULT_GAME_TILES_HORIZONTALLY + DEFAULT_BORDER);
    public static final int DEFAULT_GAME_HEIGHT = DEFAULT_CELL_SIZE * (DEFAULT_GAME_TILES_VERTICALLY + DEFAULT_BORDER);
    public static final int DEFAULT_PLAYER_HP = 3;
    public static final int BASIC_BOMB_EXPLOSION_TIMER = 5;
    public static final int DEFAULT_NODE_COST = 1;
    public static String PLAYER_1_NAME = "Player 1";
    public static String PLAYER_2_NAME = "Player 2";
    public static String PLAYER_3_NAME = "Player 3";
    public static String PLAYER_4_NAME = "Player 4";
    public static final int DEFAULT_SPEED = 4;
}