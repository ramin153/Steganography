import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class Encryption {
    protected static   String DESEncrypt(String code,String secretKey) throws Exception {
        SecretKeySpec secretKeyDES = createSecretKeyDES(secretKey);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeyDES);
        String hashString =  Base64.getEncoder().encodeToString(cipher.doFinal(code.getBytes("UTF-8")));
        return hashString;
    }
    protected static   String AESEncrypt(String code,String secretKey) throws Exception {
        SecretKeySpec secretKeyAES = createSecretKeyAES(secretKey);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeyAES);
        String hashString =  Base64.getEncoder().encodeToString(cipher.doFinal(code.getBytes("UTF-8")));
        return hashString;
    }

    protected static String DESDecrypt(String hashString,String secretKey) throws Exception {
        SecretKeySpec secretKeyDES = createSecretKeyDES(secretKey);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeyDES);
        return new String(cipher.doFinal(Base64.getDecoder().decode(hashString)));
    }
    protected static String AESDecrypt(String hashString,String secretKey) throws Exception {
        SecretKeySpec secretKeyAES = createSecretKeyAES(secretKey);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKeyAES);
        return new String(cipher.doFinal(Base64.getDecoder().decode(hashString)));
    }

    private static   SecretKeySpec createSecretKeyAES(String key) throws Exception {


        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] keyByte = sha.digest(key.getBytes("UTF-8"));
        return new SecretKeySpec(Arrays.copyOf(keyByte, 16), "AES");

    }

    private static   SecretKeySpec createSecretKeyDES(String key) throws Exception {


        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] keyByte = sha.digest(key.getBytes("UTF-8"));
        return new SecretKeySpec(Arrays.copyOf(keyByte, 8), "DES");

    }
}
