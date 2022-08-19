import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LSBEnhance  {

    private PixelImage image;

    public LSBEnhance(PixelImage image) {
        this.image = image;
    }


    public void decode(String code) throws Exception {

        byte[] codeBytes = code.getBytes(StandardCharsets.UTF_8);
        codeBytes = Arrays.copyOf(codeBytes,codeBytes.length+2);
        codeBytes[codeBytes.length-1]=0;
        codeBytes[codeBytes.length-2]=0;

        if (image.getNumberOfPixel() * 3 < codeBytes.length * 8 + 8)
            throw new Exception("we need more pixel for handling this text");

        int pixelNumber = 0;
        for (int i =0 ; i < codeBytes.length; i++){

            pixelNumber = decodePixel(image,pixelNumber,codeBytes[i]);
        }
    }

    private int decodePixel(PixelImage image, int pixelNumberRGB, byte byteCode) {
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
                    int xorSign = getXorThreeSingBit(pixelRed) ^ (1 & pixelRed);
                    int xorSingAndValue = (((xorSign) == ((selectBit)&1))?0:1);
                    image.setRedPixelColor(heightPos,widthPos,
                            (pixelRed ) ^ xorSingAndValue
                            );
                }

                case 1 ->{
                    int pixelGreen = image.getGreenPixelColor(heightPos,widthPos);
                    int xorSign = getXorThreeSingBit(pixelGreen) ^ (1 & pixelGreen);
                    int xorSingAndValue = (((xorSign) == ((selectBit)&1))?0:1);

                    image.setGreenPixelColor(heightPos,widthPos,
                            (pixelGreen ) ^ xorSingAndValue
                    );
                }

                default ->{
                    int pixelBlue = image.getBluePixelColor(heightPos,widthPos);
                    int xorSign = getXorThreeSingBit(pixelBlue) ^ (1 & pixelBlue);
                    int xorSingAndValue = (((xorSign) == ((selectBit)&1))?0:1);


                    image.setBluePixelColor(heightPos,widthPos,
                            (pixelBlue ) ^ xorSingAndValue
                    );
                }

            }
            pixelNumberRGB++;


        }

        return pixelNumberRGB;
    }

    private int getXorThreeSingBit(int num){
        return ((num>>7)&1) ^ ((num>>6)&1) ^ ((num>>5)&1);
    }


    public String encode() {
        int width = image.getWidth();
        int height = image.getHeight();
        int pixelRGBCount = 0;
        List<Byte> codeBytes = new ArrayList<>();
        byte byteCode = 0;


        for (int h = 0;h< height;h++) {


            for (int w = 0; w < width; w++) {

                for (int i = 0; i < 3; i++) {
                    int posInRGBPixel = pixelRGBCount % 8;

                    switch (pixelRGBCount % 3) {
                        case 0 -> {
                            int pixelRed = image.getRedPixelColor(h,w);
                            int xorSign = getXorThreeSingBit(pixelRed);
                            byteCode += (xorSign ^(pixelRed&1)) << (posInRGBPixel);
                        }

                        case 1 -> {
                            int pixelGreen = image.getGreenPixelColor(h,w);
                            int xorSign = getXorThreeSingBit(pixelGreen);

                            byteCode += (xorSign ^(pixelGreen&1)) << (posInRGBPixel);
                        }

                        default -> {
                            int pixelBlue = image.getBluePixelColor(h,w);
                            int xorSign = getXorThreeSingBit(pixelBlue);

                            byteCode += (xorSign ^(pixelBlue&1)) << (posInRGBPixel);
                        }
                    }

                    if (pixelRGBCount%8 == 7) {

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
