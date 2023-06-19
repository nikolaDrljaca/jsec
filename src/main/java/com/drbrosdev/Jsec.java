package com.drbrosdev;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Jsec {
    private final byte[] key = "pK1tJrfsgiLvPWJl".getBytes(Charset.defaultCharset());
    private final byte[] userKey;
    private Cipher cipher;
    private static Jsec INSTANCE = null;

    public static Jsec getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Jsec();
        }
        return INSTANCE;
    }

    private Jsec() {
        try {
            this.cipher = Cipher.getInstance("AES");
            this.userKey = initUserKey();
        } catch (Exception e) {
            throw new IllegalArgumentException("");
        }
    }

    private byte[] initUserKey() {
        try {
            var keyFilePath = Path.of(System.getProperty("user.home"), ".jsec.txt");
            var keyFile = keyFilePath.toFile();

            if (keyFile.isFile()) {
                var content = Files.readAllBytes(keyFilePath);
                if (content.length == 0)
                    throw new IllegalStateException("Key file contents are empty. Please create a new key.");
                return decryptContent(content, key);
            }

            return key;
        } catch (Exception e) {
            return key;
        }
    }

    public byte[] encryptContent(byte[] content) throws Exception {
        return encryptContent(content, userKey);
    }

    private byte[] encryptContent(byte[] content, byte[] key) throws Exception {
        var spec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, spec);
        return cipher.doFinal(content);
    }

    public byte[] decryptContent(byte[] encContent) throws Exception {
        return decryptContent(encContent, userKey);
    }

    private byte[] decryptContent(byte[] encContent, byte[] key) throws Exception {
        var spec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, spec);
        return cipher.doFinal(encContent);
    }
}
