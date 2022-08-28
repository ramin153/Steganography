import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PVD {

    //regions until 15 difference we consider lower band and upper band difference only one bit so we can't hide anything
    //from 16 util 31 we consider 2 bit -> 16-17,18-19,.....
    //from 32 util 63 we consider 4 bit -> 32-37,38-41,.....
    //from 64 util 127 we consider 8 bit -> 64-71,72-79,...
    //from 128 until 255 we consider 16 bit -> 128-143,144-159....
    PixelImage image;

    public PVD(PixelImage image) {
        this.image = image;
    }
    static byte[] save;

    public void decode(String code) throws Exception {
        byte[] codeBytes = code.getBytes(StandardCharsets.UTF_8);
        int bitNumber = 0;
        codeBytes = Arrays.copyOf(codeBytes,codeBytes.length+2);
        codeBytes[codeBytes.length-1]=0;
        codeBytes[codeBytes.length-2]=0;



        save = codeBytes;
        int i;

        for ( i = 0; (bitNumber / 8) < codeBytes.length  && i < (image.getNumberOfPixel()) - 1;i+=2){

            bitNumber = pixelChangeColor(codeBytes, bitNumber, i);

        }

        byte[] makeRestZero = new byte[]{(byte) 0,(byte)0,(byte)0,(byte)0};
        for (; i < (image.getNumberOfPixel()) - 1;i+=2){
            pixelChangeColor(makeRestZero, 0, i);

        }


        if ((bitNumber / 8) < codeBytes.length)
            throw new Exception("we can not save all the text in image");
    }

    private int pixelChangeColor(byte[] codeBytes, int bitNumber, int selectPixel) {
        int width = image.getWidth();
        int pixelOne = image.getRedPixelColor(selectPixel /width, selectPixel %width);
        int pixelTwo = image.getRedPixelColor((selectPixel +1) /width, (selectPixel +1) %width);
        int[] result;
        try {
            result = calculateNewPixel(pixelOne,pixelTwo,codeBytes,bitNumber);

            image.setRedPixelColor(selectPixel /width, selectPixel %width,result[0]);
            image.setRedPixelColor((selectPixel +1)/width,(selectPixel +1)%width,result[1]);

            bitNumber = result[2];
        }catch (Exception ignored){
            image.setRedPixelColor((selectPixel +1)/width,(selectPixel +1)%width,pixelOne);
        }


        pixelOne = image.getGreenPixelColor(selectPixel /width, selectPixel %width);
        pixelTwo = image.getGreenPixelColor((selectPixel +1) /width, (selectPixel +1) %width);

        try {
            result = calculateNewPixel(pixelOne,pixelTwo,codeBytes,bitNumber);

            image.setGreenPixelColor(selectPixel /width, selectPixel %width,result[0]);
            image.setGreenPixelColor((selectPixel +1)/width,(selectPixel +1)%width,result[1]);


            bitNumber = result[2];
        }catch (Exception ignored){
            image.setGreenPixelColor((selectPixel +1)/width,(selectPixel +1)%width,pixelOne);
        }



        pixelOne = image.getBluePixelColor(selectPixel /width, selectPixel %width);
        pixelTwo = image.getBluePixelColor((selectPixel +1) /width, (selectPixel +1) %width);


        try {
            result = calculateNewPixel(pixelOne,pixelTwo,codeBytes,bitNumber);


            image.setBluePixelColor(selectPixel /width, selectPixel %width,result[0]);
            image.setBluePixelColor((selectPixel +1)/width,(selectPixel +1)%width,result[1]);

            bitNumber = result[2];

        }catch (Exception ignored){
            image.setBluePixelColor((selectPixel +1)/width,(selectPixel +1)%width,pixelOne);
        }


        return bitNumber;
    }


    private static int[] calculateNewPixel(int pixelOne,int pixelTwo,byte[] secreteByte,int numberBitPixel) throws Exception {
        int help = numberBitPixel;
        int difference = Math.abs(pixelOne-pixelTwo);
        int[] lowAndUp =calculateUpLowerBand(difference);
        int lowerBand = lowAndUp[0];
        int upperBand = lowAndUp[1];
        int numberBitCanHide = log((upperBand - lowerBand + 1),2);
        int secretValue = 0;
        for (int i = 0;i<numberBitCanHide;i++){
            int byteNumber = numberBitPixel/8;
            int bitNumber = numberBitPixel%8;
            if (numberBitPixel < secreteByte.length * 8){
                numberBitPixel++;
                secretValue = (secretValue<<1)+getValueOfBit(secreteByte[byteNumber],(7-bitNumber)%8);
            }
            else {
                secretValue *=2;
            }
        }

        int differenceNew = lowerBand + secretValue;

        int[] newPixel = getNewPixelValueBaseOnDifference(pixelOne, pixelTwo, difference, differenceNew);


        if (newPixel[0] < 0 || newPixel[1] <0 || newPixel[0] > 255 ||newPixel[1] > 255){

            throw new Exception("it can not applied");

        }

        int[] result = Arrays.copyOf(newPixel,3);
        result[2] = numberBitPixel;
        return result;
    }

    private static int[] getNewPixelValueBaseOnDifference(int pixelOne, int pixelTwo, int difference, int differenceNew) {
        int[] newPixelValue;
        double totalDifference =  ((double) Math.abs(differenceNew - difference))/2;


        if ((differenceNew > difference && pixelOne >= pixelTwo) ||(differenceNew <= difference && pixelOne < pixelTwo) )
        {

            newPixelValue = new int[]{pixelOne + (int)Math.ceil(totalDifference), pixelTwo - (int)Math.floor(totalDifference)};
        } else if (differenceNew > difference && pixelOne < pixelTwo) {
            newPixelValue = new int[]{pixelOne - (int)Math.floor(totalDifference), pixelTwo + (int)Math.ceil(totalDifference)};
        }else {
            newPixelValue = new int[]{pixelOne - (int)Math.ceil(totalDifference), pixelTwo + (int)Math.floor(totalDifference)};
        }
        return newPixelValue;
    }

    private static int log(int number, int base) {
        return (int) (Math.log(number) / Math.log(base));
    }

    private static int getValueOfBit(byte input,int bitNumber){
        return (input >> bitNumber) & 1;
    }
    private static int[]  calculateUpLowerBand(int difference) throws Exception {
        int lowerBand;
        int upperBan;
        if (difference <= 15){
            lowerBand = difference;
            upperBan = difference;
        } else if (difference <= 31) {
            lowerBand = difference - (difference %2);
            upperBan = lowerBand + 1;
        } else if (difference <= 63) {
            lowerBand = difference - (difference %4);
            upperBan = lowerBand + 3;
        }else if ( difference <= 127) {
            lowerBand = difference - (difference %8);
            upperBan = lowerBand + 7;
        }else if ( difference <= 255) {
            lowerBand = difference - (difference %16);
            upperBan = lowerBand + 15;
        }else {
            throw new Exception("there is problem with input value please check it");
        }
        return new int[]{lowerBand,upperBan};
    }


    public String encode() {

        List<Byte> bytesCode = new ArrayList<>();
        byte byteCode = 0;
        int bitNumber = 0;
        int width = image.getWidth();
        int pixelOne,pixelTwo;
        for (int i=0; i < (image.getNumberOfPixel()) - 1;i+=2)
        {


            pixelOne = image.getRedPixelColor(i /width, i %width);
            pixelTwo = image.getRedPixelColor((i +1) /width, (i +1) %width);

            Object[] bitNumberAndByte = calculateSecretValue(pixelOne,pixelTwo,bitNumber,byteCode,bytesCode);
            assert bitNumberAndByte != null;
            bitNumber = (int)bitNumberAndByte[0];
            byteCode = (byte)bitNumberAndByte[1];



            pixelOne = image.getGreenPixelColor(i /width, i %width);
            pixelTwo = image.getGreenPixelColor((i +1) /width, (i +1) %width);

            bitNumberAndByte = calculateSecretValue(pixelOne,pixelTwo,bitNumber,byteCode,bytesCode);
            bitNumber = (int)bitNumberAndByte[0];
            byteCode = (byte)bitNumberAndByte[1];



            pixelOne = image.getBluePixelColor(i /width, i %width);
            pixelTwo = image.getBluePixelColor((i +1) /width, (i +1) %width);

            bitNumberAndByte = calculateSecretValue(pixelOne,pixelTwo,bitNumber,byteCode,bytesCode);
            bitNumber = (int)bitNumberAndByte[0];
            byteCode = (byte)bitNumberAndByte[1];


        }
        if (bitNumber != 0)
            bytesCode.add((byte)(byteCode << (8 - bitNumber)));


        Byte[] bytesClass = bytesCode.toArray(new Byte[bytesCode.size()]);

        byte[] bytes = new byte[bytesClass.length];
        for (int i = 0; i < bytesClass.length;i++){
            bytes[i] = bytesClass[i];
        }
        String result = new String(bytes, StandardCharsets.UTF_8);

        int i;
        for (i = 0 ; i < result.length();i++){
            if((int)result.charAt(i)== 0)
                break;
        }
        return result.substring(0,i);

    }

    private static Object[] calculateSecretValue(int colorOne,int colorTwo,int bitNumber,byte byteCode,List<Byte> bytesCode)
    {
        int help = bitNumber + bytesCode.size()*8;
        int[] secretCodeAndBits ;
        try {
            secretCodeAndBits = getSecretTwoPixel(colorOne,colorTwo);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        int secretValue = secretCodeAndBits[0];

        int numBitInSecretValue = secretCodeAndBits[1];

        if (numBitInSecretValue + bitNumber < 8){
            bitNumber += numBitInSecretValue;
            byteCode = (byte) (((byte)(byteCode << numBitInSecretValue)) + ((byte) secretValue));

        }else {
            int numberBitCanAddNow = numBitInSecretValue - ((numBitInSecretValue+bitNumber)%8);

            byteCode = (byte) (((byte)(byteCode << numberBitCanAddNow)) +
                    ((byte)(getPartOfByteUper((byte) secretValue,
                            8 - (numBitInSecretValue - numberBitCanAddNow)) >> (numBitInSecretValue - numberBitCanAddNow)))   );
            bytesCode.add(byteCode);

            byteCode =  getPartOfByte((byte) secretValue , numBitInSecretValue - numberBitCanAddNow);

            bitNumber = numBitInSecretValue - numberBitCanAddNow;
        }

        return new Object[]{bitNumber,byteCode};
    }

    private static byte getPartOfByte(byte input,int numberOfBit){
        int mask = 0;
        for (int i = 0;i < numberOfBit;i++){
            mask *=2;
            mask +=1;
        }
        return (byte) (mask & input);
    }

    private static byte getPartOfByteUper(byte input,int numberOfBit){
        int mask = 0;
        for (int i = 0;i < numberOfBit;i++){
            mask /=2;
            mask +=128;
        }
        return (byte) (mask & input);
    }

    private static int[] getSecretTwoPixel(int pixelOne,int pixelTwo) throws Exception {
        int difference = Math.abs(pixelOne-pixelTwo);

        int lowerBand;
        int upperBand;
        if (difference <= 15){
            lowerBand = difference;
            upperBand = difference;
        } else if (difference <= 31) {
            lowerBand = difference - (difference %2);
            upperBand = lowerBand + 1;
        } else if ( difference <= 63) {
            lowerBand = difference - (difference %4);
            upperBand = lowerBand + 3;
        }else if ( difference <= 127) {
            lowerBand = difference - (difference %8);
            upperBand = lowerBand + 7;
        }else if (difference <= 255) {
            lowerBand = difference - (difference %16);
            upperBand = lowerBand + 15;
        }else {
            throw new Exception("there is problem with input value please check it");
        }

        int numberBitHide = log((upperBand - lowerBand + 1),2);

        return new int[]{difference-lowerBand,numberBitHide};

    }

    public  void DESEncrypt(String code,String secretKey) throws Exception {
        decode(Encryption.DESEncrypt(code,secretKey));
    }

    public  void AESEncrypt(String code,String secretKey) throws Exception {
        String text = Encryption.AESEncrypt(code,secretKey);

        decode(text);
    }

    public  String DESDecrypt(String secretKey) throws Exception {

        return Encryption.DESDecrypt(encode(),secretKey);
    }


    public String AESDecrypt(String secretKey) throws Exception {
        String text = encode();

        return Encryption.AESDecrypt(text,secretKey);
    }


}
