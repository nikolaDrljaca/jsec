package com.drbrosdev;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

public class Jsec {
    //TODO: Read this from the .properties file or env variable
    //Initialize in constructor and use unchecked exceptions -> IllegalStateException etc
    private final byte[] key = "iamtheexampleKey".getBytes(Charset.defaultCharset());
    private SecretKeySpec secretKeySpec;
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
            this.secretKeySpec = new SecretKeySpec(key, "AES");
            this.cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            throw new IllegalArgumentException("");
        }
    }

    public byte[] encryptContent(byte[] content) throws Exception {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decryptContent(byte[] encContent) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return cipher.doFinal(encContent);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
