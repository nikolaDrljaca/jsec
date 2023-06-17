package com.drbrosdev;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

public class Jsec {
    //TODO: Read this from the .properties file or env variable
    //Initialize in constructor and use unchecked exceptions -> IllegalStateException etc
    private final byte[] key = "iamtheexampleKey".getBytes(Charset.defaultCharset());

    public byte[] encryptContent(byte[] content) throws Exception {
        try {
            var keySpec = new SecretKeySpec(key, "AES");
            var cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decryptContent(byte[] encContent) {
        try {
            var keySpec = new SecretKeySpec(key, "AES");
            var cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return cipher.doFinal(encContent);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
