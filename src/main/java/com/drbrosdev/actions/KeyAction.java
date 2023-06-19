package com.drbrosdev.actions;

import com.drbrosdev.Jsec;

import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class KeyAction implements Action {

    private final Path keyFilePath;
    private String key;

    public KeyAction(String inKey) {
        this.keyFilePath = Path.of(System.getProperty("user.home"),".jsec.txt");
        this.key = inKey;
    }

    @Override
    public void run() {
        try {
            if (key.length() < 16) {
                key = String.format("%1$" + 16 + "s", key).replace(' ', '0');
            }
            if(key.length() > 16) {
                key = key.substring(0, 16);
            }

            var keyFile = keyFilePath.toFile();
            var in = key.getBytes(Charset.defaultCharset());
            var jsec = Jsec.getInstance();
            var encryptedData = jsec.encryptContent(in);

            if (!keyFile.isFile()) {
                keyFile.createNewFile();
            }

            try (FileOutputStream stream = new FileOutputStream(keyFile)) {
                stream.write(encryptedData);
                System.out.println("Key saved.");
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("\n---DEVELOPER INFO---");
            e.printStackTrace();
        }
    }
}
