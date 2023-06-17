package com.drbrosdev;

import com.drbrosdev.argparser.ArgParser;

import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

public class Main {
    public static void main(String[] args) {
//        System.out.println(System.getProperty("user.home"));
        try {
            var argParser = new ArgParser(args);
            switch (argParser.action()) {
                case ENCRYPT -> {
                    System.out.println("Encrypting " + argParser.inFilePath() + "...");
                    var content = Files.readAllBytes(argParser.inFilePath());
                    var jsec = Jsec.getInstance();
                    var encryptedData = jsec.encryptContent(content);

                    try (FileOutputStream stream = new FileOutputStream(argParser.outFilePath().toString())) {
                        stream.write(encryptedData);
                        System.out.println("Finished.");
                    }
                }
                case DECRYPT -> {
                    System.out.println("Decrypting " + argParser.inFilePath() + "...");
                    var content = Files.readAllBytes(argParser.inFilePath());
                    var jsec = Jsec.getInstance();
                    var decryptContent = jsec.decryptContent(content);
                    System.out.println("---" + argParser.inFilePath().getFileName() + " CONTENT---");
                    System.out.println(new String(decryptContent, Charset.defaultCharset()));

                    try (FileOutputStream stream = new FileOutputStream(argParser.outFilePath().toString())) {
                        stream.write(decryptContent);
                        System.out.println("Finished.");
                    }
                }
                case HELP -> {
                    System.out.println("Pretty print some help commands here.");
                }
            }
        } catch (NoSuchFileException noSuchFileException) {
            System.out.println("File does not exist.");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("---DEVELOPER INFO---");
            e.printStackTrace();
        }
    }
}