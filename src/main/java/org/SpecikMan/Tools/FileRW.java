package org.SpecikMan.Tools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileRW {
    public static void Write(String path, String content) {
        try {
            Path fileName = Path.of(path);
            Files.writeString(fileName, content);
        } catch (IOException ignored) {
        }
    }
    public static String Read(String path) {
        try{
            Path fileName = Path.of(path);
            return Files.readString(fileName);
        }catch(IOException ignored){
            return null;
        }
    }
}
