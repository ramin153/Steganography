import java.nio.charset.StandardCharsets;
import java.util.*;

public class RandomPixelEmbedding {
    private PixelImage image;

    public RandomPixelEmbedding(PixelImage image) {
        this.image = image;
    }

    private static int uniqueRandom(Random rand, int maxSize, LinkedHashSet<Integer> numberSeen)
    {
        if (maxSize <= numberSeen.size())
            return -1;
        int newNumber = rand.nextInt(maxSize);

        int oldSize = numberSeen.size();

        while (true){
            numberSeen.add(newNumber);
            if (oldSize != numberSeen.size())
                break;
            newNumber = (newNumber+1)%maxSize;
        }

        return newNumber;

    }
    public void decode(String secretCode,int seed) throws Exception {


        byte[] codeBytes = secretCode.getBytes(StandardCharsets.UTF_8);
        codeBytes = Arrays.copyOf(codeBytes,codeBytes.length+1);
        codeBytes[codeBytes.length-1] = 0;
        if (image.getNumberOfPixel() * 3 < codeBytes.length * 8 + 8)
            throw new Exception("we need more pixel for handling this text");

        int bitNumber = 0;
        int imageSize = image.getNumberOfPixel();
        Random random = new Random(seed);
        LinkedHashSet<Integer> numberSeen = new LinkedHashSet<>();
        while (bitNumber < codeBytes.length * 8){
            int pixelNumber = uniqueRandom(random,imageSize,numberSeen);
            bitNumber = decodePixel(image,pixelNumber,codeBytes,bitNumber);
        }
    }

    private static int decodePixel(PixelImage image, int pixelNumberRGB, byte[] byteCodes,int bitNumber) {

        for (int i = 0;i < 3 ; i++)
        {
            if (bitNumber >= byteCodes.length*8)
                break;
            int widthPos = pixelNumberRGB%image.getWidth();
            int heightPos = pixelNumberRGB/image.getWidth();

            byte byteCode = byteCodes[bitNumber/8];
            int bitNumberInByte = bitNumber % 8;
            int selectBit = (byteCode >> bitNumberInByte) & 1;

            switch (i)
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
            bitNumber++;


        }


        return bitNumber;

    }

    private static int getXorTwoLowBit(int pixelRed) {

        return (pixelRed &1) ^    ((pixelRed>>1)&1);

    }


    public String encode(int seed) {

        int size = image.getNumberOfPixel();

        int bitNumber = 0;
        List<Byte> codeBytes = new ArrayList<>();
        Random random = new Random(seed);
        LinkedHashSet<Integer> numberSeen = new LinkedHashSet<>();
        byte byteCode = 0;
        int checkNumber = 100;
        while (numberSeen.size() < size)
        {
            int pixelNumber = uniqueRandom(random,size,numberSeen);
            int width = pixelNumber%image.getWidth();
            int height = pixelNumber/image.getWidth();
            if (checkNumber < bitNumber)
            {
                checkNumber+= checkNumber/2;
                if (checkForEnd(codeBytes))
                    break;
            }
            int dataInRedPixel = getXorTwoLowBit(image.getRedPixelColor(height, width));
            byteCode += (byte) (dataInRedPixel) << (bitNumber%8);
            bitNumber++;
            if (bitNumber %8 == 0){
                codeBytes.add(byteCode);
                byteCode = 0;
            }


            int dataInGreenPixel = getXorTwoLowBit(image.getGreenPixelColor(height, width));
            byteCode += (byte) (dataInGreenPixel) << (bitNumber%8);
            bitNumber++;
            if (bitNumber %8 == 0){
                codeBytes.add(byteCode);
                byteCode = 0;
            }


            int dataInBluePixel = getXorTwoLowBit(image.getBluePixelColor(height, width));
            byteCode += (byte) (dataInBluePixel) << (bitNumber%8);
            bitNumber++;
            if (bitNumber %8 == 0){
                codeBytes.add(byteCode);
                byteCode = 0;
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

    private static boolean checkForEnd(List<Byte> codeBytes)
    {
        Byte[] bytesClass = codeBytes.toArray(new Byte[codeBytes.size()]);

        byte[] bytes = new byte[bytesClass.length];
        for (int i = 0; i < bytesClass.length; i++) {
            bytes[i] = bytesClass[i];
        }
        String result = new String(bytes, StandardCharsets.UTF_8);
        int i = 0;
        for (; i < result.length(); i++) {
            if ((int) result.charAt(i) == 0)
                return true;
        }
        return false;
    }




    public  void AESEncrypt(String code,String secretKey,int seed) throws Exception {

        decode(Encryption.AESEncrypt(code,secretKey),seed);
    }

    public  void DESEncrypt(String code,String secretKey,int seed) throws Exception {

        decode(Encryption.DESEncrypt(code,secretKey),seed);
    }

    public  String DESDecrypt(String secretKey,int seed) throws Exception {

        return Encryption.DESDecrypt(encode(seed),secretKey);
    }


    public String AESDecrypt(String secretKey,int seed) throws Exception {

        return Encryption.AESDecrypt(encode(seed),secretKey);
    }





}
