import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LSB {

    private PixelImage image;

    public LSB(PixelImage image) {
        this.image = image;
    }

    public void decode(String secretCode) throws Exception {

        byte[] codeBytes = secretCode.getBytes(StandardCharsets.UTF_8);
        codeBytes = Arrays.copyOf(codeBytes,codeBytes.length+1);
        codeBytes[codeBytes.length-1] = 0;
        if (image.getNumberOfPixel() * 3 < codeBytes.length * 8 + 8)
            throw new Exception("we need more pixel for handling this text");

        int pixelNumber = 0;
        for (byte cb:codeBytes){
            pixelNumber = decodePixel(image,pixelNumber,cb);
        }
    }

    private static int decodePixel(PixelImage image, int pixelNumberRGB, byte byteCode) {

        for (int i = 0;i < 8 ; i++)
        {
            int pixelNumber = pixelNumberRGB / 3;
            int widthPos = pixelNumber%image.getWidth();
            int heightPos = pixelNumber/image.getWidth();

            int selectBit = (byteCode >> i) & 1;

            switch (pixelNumberRGB%3)
            {
                case 0 ->{
                    int pixelRed = image.getRedPixelColor(heightPos,widthPos);
                    int xor = getXorTwoLowBit(pixelRed) ;

                    int lsb = (xor == selectBit) ? 0:1;
                    image.setRedPixelColor(heightPos,widthPos,
                            (pixelRed ) ^ (lsb)
                    );
                }

                case 1 ->{
                    int pixelGreen = image.getGreenPixelColor(heightPos,widthPos);
                    int xor = getXorTwoLowBit(pixelGreen);
                    int lsb = (xor == selectBit) ? 0:1;
                    image.setGreenPixelColor(heightPos,widthPos,
                            (pixelGreen ) ^ (lsb)
                    );

                }

                default ->{
                    int pixelBlue = image.getBluePixelColor(heightPos,widthPos);
                    int xor = getXorTwoLowBit(pixelBlue);
                    int lsb = (xor == selectBit) ? 0:1;
                    image.setBluePixelColor(heightPos,widthPos,
                            (pixelBlue ) ^  (lsb)
                    );

                }

            }
            pixelNumberRGB++;


        }


        return pixelNumberRGB;

    }

    private static int getXorTwoLowBit(int pixelRed) {

        return (pixelRed &1) ^    ((pixelRed>>1)&1);

    }


    public String encode() {

        int width = image.getWidth();
        int height = image.getHeight();
        int pixelRGBCount = 0;
        List<Byte> codeBytes = new ArrayList<>();
        byte byteCode = 0;


        for (int h = 0; h < height; h++) {


            for (int w = 0; w < width; w++) {

                for (int i = 0; i < 3; i++) {
                    int posInRGBPixel = pixelRGBCount % 8;

                    switch (pixelRGBCount % 3) {
                        case 0 -> {
                            int pixelRed = image.getRedPixelColor(h, w);
                            int xor = getXorTwoLowBit(pixelRed);
                            byteCode += (byte) (xor) << (posInRGBPixel);
                        }

                        case 1 -> {
                            int pixelGreen = image.getGreenPixelColor(h, w);
                            int xor = getXorTwoLowBit(pixelGreen);

                            byteCode += (byte) (xor) << (posInRGBPixel);
                        }

                        default -> {
                            int pixelBlue = image.getBluePixelColor(h, w);
                            int xor = getXorTwoLowBit(pixelBlue);

                            byteCode += (byte) (xor) << (posInRGBPixel);
                        }
                    }

                    if (pixelRGBCount % 8 == 7) {

                        codeBytes.add(byteCode);

                        byteCode = 0;
                    }
                    pixelRGBCount++;
                }
            }
        }

        Byte[] bytesClass = codeBytes.toArray(new Byte[codeBytes.size()]);

        byte[] bytes = new byte[bytesClass.length];
        for (int i = 0; i < bytesClass.length; i++) {
            bytes[i] = bytesClass[i];
        }
        String result = new String(bytes, StandardCharsets.UTF_8);
        int i = 0;
        for (; i < result.length(); i++) {
            if ((int) result.charAt(i) == 0)
                break;
        }

        return result.substring(0, i);
    }

    public  void DESEncrypt(String code,String secretKey) throws Exception {
        decode(Encryption.DESEncrypt(code,secretKey));
    }



    public  void AESEncrypt(String code,String secretKey) throws Exception {

        decode(Encryption.AESEncrypt(code,secretKey));
    }

    public  String DESDecrypt(String secretKey) throws Exception {

        return Encryption.DESDecrypt(encode(),secretKey);
    }


    public String AESDecrypt(String secretKey) throws Exception {

        return Encryption.AESDecrypt(encode(),secretKey);
    }
}
