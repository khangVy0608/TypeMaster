package org.SpecikMan.Tools;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileRW {
    public static void Write(String path, String content) {
        try {
            Path fileName = Path.of(path);
            Files.writeString(fileName,content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String Read(String path) {
        try{
            Path fileName = Path.of(path);
            return Files.readString(fileName);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
