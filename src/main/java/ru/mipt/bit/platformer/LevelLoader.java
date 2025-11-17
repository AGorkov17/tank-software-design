package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Optional;

/**
 * Loads a level layout from a text file where T=tree, X=player spawn, _ or space = empty.
 */
public final class LevelLoader {

    private LevelLoader() {
    }

    public static Optional<Level> loadLevelFromFile() {
        try {
            FileHandle handle = Gdx.files.internal("level.txt");
            if (!handle.exists()) {
                return Optional.empty();
            }
            String layout = handle.readString();
            return Optional.of(parseLevel(layout));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static Level parseLevel(String layout) {
        String[] rows = layout.split("\\r?\\n");
        int height = rows.length;
        int width = 0;
        for (String row : rows) {
            width = Math.max(width, row.length());
        }
        Level level = new Level(width, height);
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            String row = rows[rowIndex];
            for (int column = 0; column < row.length(); column++) {
                char cell = row.charAt(column);
                int x = column;
                int y = height - 1 - rowIndex;
                GridPoint2 tile = new GridPoint2(x, y);
                switch (cell) {
                    case 'T':
                        level.addObstacle(tile);
                        break;
                    case 'X':
                        level.setPlayerSpawn(tile);
                        break;
                    default:
                        break;
                }
            }
        }
        if (level.getPlayerSpawn() == null) {
            level.setPlayerSpawn(new GridPoint2(0, 0));
        }
        return level;
    }
}

public class LevelLoader {
    
    public static String loadLevelFromFile() {
        try {
            FileHandle file = Gdx.files.internal("level.txt");
            return file.readString();
        } catch (Exception e) {
            // Если файла нет, используем уровень по умолчанию
            return 
                "T  T  \n" +
                "T  TTT\n" +
                "T  T  \n" +
                "   T  \n" +
                "   T  \n" +
                "X     \n";
        }
    }
}
