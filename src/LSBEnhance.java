import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LSBEnhance implements SteganographyImageText {

    private PixelImage image;

    public LSBEnhance(PixelImage image) {
        this.image = image;
    }

    @Override
    public void decode(String code) throws Exception {

        byte[] codeBytes = code.getBytes(StandardCharsets.UTF_8);
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
                    int xorSign = getXorThreeSingBit(pixelRed);

                    image.setRedPixelColor(heightPos,widthPos,
                            (pixelRed ) ^ (xorSign) ^ (selectBit)
                            );
                }

                case 1 ->{
                    int pixelGreen = image.getGreenPixelColor(heightPos,widthPos);
                    int xorSign = getXorThreeSingBit(pixelGreen);

                    image.setGreenPixelColor(heightPos,widthPos,
                            (pixelGreen ) ^ (xorSign) ^ (selectBit)
                    );
                }

                default ->{
                    int pixelBlue = image.getBluePixelColor(heightPos,widthPos);
                    int xorSign = getXorThreeSingBit(pixelBlue);


                    image.setBluePixelColor(heightPos,widthPos,
                            (pixelBlue ) ^ (xorSign) ^ (selectBit)
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

    @Override
    public String encode(PixelImage rawImage) {
        int width = rawImage.getWidth();
        int height = rawImage.getHeight();
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
                            byteCode +=
                                    (byte) ((image.getRedPixelColor(h, w) ^ rawImage.getRedPixelColor(h, w) ^ (xorSign))
                                            << (posInRGBPixel));
                        }

                        case 1 -> {
                            int pixelGreen = image.getGreenPixelColor(h,w);
                            int xorSign = getXorThreeSingBit(pixelGreen);

                            byteCode +=
                                    (byte) ((image.getGreenPixelColor(h, w) ^ rawImage.getGreenPixelColor(h, w) ^ (xorSign))
                                            << (posInRGBPixel ));
                        }

                        default -> {
                            int pixelBlue = image.getBluePixelColor(h,w);
                            int xorSign = getXorThreeSingBit(pixelBlue);

                            byteCode +=
                                    (byte) ((image.getBluePixelColor(h, w) ^ rawImage.getBluePixelColor(h, w)^(xorSign))
                                            << (posInRGBPixel ));
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

    public  String DESDecrypt(PixelImage rawImage,String secretKey) throws Exception {

        return Encryption.DESDecrypt(encode(rawImage),secretKey);
    }
    public String AESDecrypt(PixelImage rawImage,String secretKey) throws Exception {
        return Encryption.AESDecrypt(encode(rawImage),secretKey);
    }
}
