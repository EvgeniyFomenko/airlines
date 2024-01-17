package ru.efomenko.util;

import java.io.*;
import java.nio.file.Path;

public class FileWorker {
    public static String getContent(Path path) throws IOException {
        File file = new File(path.toUri());
        StringBuilder stringBuilder = new StringBuilder();

        try (Reader fileReader = new FileReader(file)) {
            int data = fileReader.read();
            while (data != -1) {
                stringBuilder.append((char)data);
                data = fileReader.read();
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

        return stringBuilder.toString();
    }
}
