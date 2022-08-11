import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class LSB implements SteganographyImageText{

    private PixelImage image;

    public LSB(PixelImage image) {
        this.image = image;
    }
    @Override
    public  void  decode(String code) throws Exception {
        byte[] codeBytes = code.getBytes(StandardCharsets.UTF_8);
        if (image.getNumberOfPixel() * 3 < codeBytes.length * 4 + 4)
            throw new Exception("we need more pixel for handling this text");

        int pixelNumber = 0;
        for (byte cb:codeBytes){
            pixelNumber = decodePixel(image,pixelNumber,cb);
        }
    }
    private static int decodePixel(PixelImage image,int pixelRGBSelect,byte byteCode){

        for (int i = 0;i <4;i++){
            int pixelNumber = pixelRGBSelect / 3;
            int widthPos = pixelNumber%image.getWidth();
            int heightPos = pixelNumber/image.getWidth();

            int pixelLSB = ((byteCode >> (i*2)) & 1)  + 2*((byteCode >> (i*2+1)) & 1) ;

            switch (pixelRGBSelect%3){
                case 0 ->{
                    image.setRedPixelColor(heightPos,widthPos, (
                            image.getRedPixelColor(heightPos,widthPos) ^ pixelLSB
                            ));
                }
                case 1 -> {
                    image.setGreenPixelColor(heightPos,widthPos, (
                            image.getGreenPixelColor(heightPos,widthPos) ^ pixelLSB
                    ));
                }
                default -> {
                    image.setBluePixelColor(heightPos,widthPos, (
                            image.getBluePixelColor(heightPos,widthPos) ^ pixelLSB
                    ));
                }
            }

            pixelRGBSelect++;
        }

        return pixelRGBSelect;
    }

    @Override
    public  String encode(PixelImage rawImage){
        int width = rawImage.getWidth();
        int height = rawImage.getHeight();
        int pixelRGBCount = 0;
        List<Byte> codeBytes = new ArrayList<>();
        byte byteCode = 0;


        for (int h = 0;h< height;h++) {


            for (int w = 0; w < width; w++) {

                for (int i = 0; i < 3; i++) {
                    int posInRGBPixel = pixelRGBCount % 4;

                    switch (pixelRGBCount % 3) {
                        case 0 -> {
                            byteCode +=
                                    (byte) ((image.getRedPixelColor(h, w) ^ rawImage.getRedPixelColor(h, w))
                                            << (posInRGBPixel * 2));
                        }

                        case 1 -> {
                            byteCode +=
                                    (byte) ((image.getGreenPixelColor(h, w) ^ rawImage.getGreenPixelColor(h, w))
                                            << (posInRGBPixel * 2));
                        }

                        default -> {
                            byteCode +=
                                    (byte) ((image.getBluePixelColor(h, w) ^ rawImage.getBluePixelColor(h, w))
                                            << (posInRGBPixel * 2));
                        }
                    }

                    if (pixelRGBCount%4 == 3) {
                        codeBytes.add(byteCode);


                        byteCode = 0;
                    }
                    pixelRGBCount++;
                }
            }
        }

        Byte[] bytesClass = codeBytes.toArray(new Byte[codeBytes.size()]);

        byte[] bytes = new byte[bytesClass.length];
        for (int i = 0; i < bytesClass.length;i++){
            bytes[i] = bytesClass[i];
        }
        String result = new String(bytes, StandardCharsets.UTF_8);
        int i=0;
        for ( i = 0 ; i < result.length();i++){
            if((int)result.charAt(i)== 0)
                break;
        }
        return result.substring(0,i);
    }
    @Override
    public  void DESEncrypt(String code,String secretKey) throws Exception {
        decode(Encryption.DESEncrypt(code,secretKey));
    }
    @Override

    public  void AESEncrypt(String code,String secretKey) throws Exception {
        decode(Encryption.AESEncrypt(code,secretKey));
    }
    @Override

    public  String DESDecrypt(PixelImage rawImage,String secretKey) throws Exception {
        return Encryption.DESDecrypt(encode(rawImage),secretKey);
    }
    @Override

    public String AESDecrypt(PixelImage rawImage,String secretKey) throws Exception {
        return Encryption.AESDecrypt(encode(rawImage),secretKey);
    }


}
