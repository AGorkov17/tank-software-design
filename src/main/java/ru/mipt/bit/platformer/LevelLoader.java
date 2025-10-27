package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

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