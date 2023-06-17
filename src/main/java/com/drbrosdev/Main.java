package com.drbrosdev;

import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {
            var filePath = Path.of("temp/foo.txt");
            var content = Files.readAllBytes(filePath);
            var jsec = new Jsec();
            var encryptedData = jsec.encryptContent(content);

            try(FileOutputStream stream = new FileOutputStream("temp/foo-enc.txt")) {
                stream.write(encryptedData);
            }

            var data = new String(jsec.decryptContent(encryptedData), Charset.defaultCharset());
            System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}