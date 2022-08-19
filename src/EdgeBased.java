import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class EdgeBased {

    PixelImage image;

    public EdgeBased(PixelImage image) {
        this.image = image;
    }


    public void decode(String code) throws Exception {
        byte[] codeBytes = code.getBytes(StandardCharsets.UTF_8);
        codeBytes = Arrays.copyOf(codeBytes,codeBytes.length+2);
        codeBytes[codeBytes.length-1]=0;
        codeBytes[codeBytes.length-2]=0;

        PixelImage maskedImage = mask2LSB(image.deepCopy());

        if ((maxSize(maskedImage)*3 - 32)< codeBytes.length*8)
        {
            throw new Exception("we can not fit text inside the image");
        }

        float threshHoldLow,threshHoldHigh;

        if ((minSize(maskedImage)*3 - 32) > codeBytes.length*8){
            threshHoldHigh = 1f;
            threshHoldLow = 1f;
        }else {
            float[] highLow = findThrashHold(maskedImage,code.length()*8+32);
            threshHoldHigh= highLow[0];
            threshHoldLow = highLow[1];
        }
        CannyEdgeDetector canny = new CannyEdgeDetector(maskedImage.getImg(),threshHoldLow,threshHoldHigh,2);
        canny.process();
        List<Integer[]> savePixel = canny.getEdges();

        decodeThreshHold(maskedImage,threshHoldHigh,threshHoldLow);

        int bitNumber =0;
        int position = 0;
        while (bitNumber < 8 * codeBytes.length)
        {

            int height = savePixel.get(position)[0];
            int width = savePixel.get(position)[1];

            bitNumber = decodePixel(height,width,image,codeBytes,bitNumber);

            position++;
        }


    }

    public String encode()
    {
        PixelImage masked = mask2LSB(image.deepCopy());

        float[] highLow =  getThreshHold(masked);
        float th = highLow[0];
        float tl = highLow[1];


        CannyEdgeDetector canny = new CannyEdgeDetector(masked.getImg(),tl,th,2);
        canny.process();
        List<Integer[]> pixelLocation = canny.getEdges();

        List<Byte> codeBytes = new ArrayList<>();
        int bitNumber = 0;
        byte byteCode = 0;
        for (Integer[] pos:pixelLocation)
        {
            int height = pos[0];
            int width = pos[1];


            Object[] obj = encodePixel(height,width,image,codeBytes,byteCode,bitNumber);
            bitNumber = (int)obj[0];
            byteCode = (byte)obj[1];


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





    private  void decodeThreshHold(PixelImage mask,float th,float tl)
    {
        List<Integer[]> noneSoft = getNoneSoftPart(mask);

        int thIEEE = Float.floatToIntBits(th);
        int tlIEEE = Float.floatToIntBits(tl);

        ByteBuffer bbTH = ByteBuffer.allocate(4);
        bbTH.putInt(thIEEE);
        byte[] byteArrayTH = bbTH.array();

        ByteBuffer bbTL  = ByteBuffer.allocate(4);
        bbTL.putInt(tlIEEE);
        byte[] byteArrayTL = bbTL.array();

        byte[] result = new byte[byteArrayTH.length + byteArrayTL.length];
        System.arraycopy(byteArrayTH, 0, result, 0, byteArrayTH.length);
        System.arraycopy(byteArrayTL, 0, result, byteArrayTH.length, byteArrayTL.length);

        int position =0;
        int bitNumber = 0;

        while (bitNumber < 8 * result.length)
        {
            int height = position/image.getHeight();
            int width = position%image.getWidth();
            if (!contain2(noneSoft,new int[]{height,width}))
            {
                bitNumber = decodePixel(height,width,image,result,bitNumber);
            }
            position++;
        }




    }

    private  float[] getThreshHold(PixelImage mask)
    {
        List<Integer[]> noneSoft = getNoneSoftPart(mask);
        List<Byte> codeBytes = new ArrayList<>();

        int position =0;
        int bitNumber = 0;
        byte byteCode = 0;

        while (codeBytes.size() < 8)
        {
            int height = position/image.getHeight();
            int width = position%image.getWidth();
            if (!contain2(noneSoft,new int[]{height,width}))
            {
                Object[] obj = encodePixel(height,width,image,codeBytes,byteCode,bitNumber);
                bitNumber = (int)obj[0];
                byteCode = (byte)obj[1];
            }
            position++;

        }

        Byte[] bytesClass = codeBytes.toArray(new Byte[codeBytes.size()]);

        byte[] bytes = new byte[bytesClass.length];
        for (int i = 0; i < bytesClass.length;i++){
            bytes[i] = bytesClass[i];
        }


        byte[] byteArrayTH = Arrays.copyOfRange(bytes,0,4);
        int thIEEE = convertBytesToInt(byteArrayTH);

        byte[] byteArrayTL = Arrays.copyOfRange(bytes,4,8);
        int tlIEEE = convertBytesToInt(byteArrayTL);

        float th = Float.intBitsToFloat(thIEEE);
        float tl = Float.intBitsToFloat(tlIEEE);

        return new float[]{th,tl};
    }

    private int convertBytesToInt(byte[] bytes)
    {
        int value = 0;
        for (byte b : bytes) {
            value = (value << 8) + (b & 0xFF);
        }
        return value;
    }
    private Object[] encodePixel(int height, int width, PixelImage image, List<Byte> bytes,byte byteCode, int bitNumber) {



        int pixelRed = image.getRedPixelColor(height, width);
        int xor = getXorTwoLowBit(pixelRed);
        byteCode += (byte) (xor) << (bitNumber%8);

        if (bitNumber % 8 == 7) {

            bytes.add(byteCode);

            byteCode = 0;
        }
        bitNumber++;

        int pixelGreen = image.getGreenPixelColor(height, width);
        xor = getXorTwoLowBit(pixelGreen);

        byteCode += (byte) (xor) << (bitNumber%8);

        if (bitNumber % 8 == 7) {

            bytes.add(byteCode);

            byteCode = 0;
        }
        bitNumber++;

        int pixelBlue = image.getBluePixelColor(height, width);
        xor = getXorTwoLowBit(pixelBlue);

        byteCode += (byte) (xor) << (bitNumber%8);



        if (bitNumber % 8 == 7) {

            bytes.add(byteCode);

            byteCode = 0;
        }
        bitNumber++;


        return new Object[]{bitNumber,byteCode};
    }

    private static int decodePixel(int height,int width,PixelImage image,byte[] bytes,int bitLocation)
    {
        int selectBit = ((bytes[bitLocation/8])>>(bitLocation%8))&1;
        int pixelRed = image.getRedPixelColor(height,width);
        int xor = getXorTwoLowBit(pixelRed) ;

        int lsb = (xor == selectBit) ? 0:1;
        image.setRedPixelColor(height,width,
                (pixelRed ) ^ (lsb)
        );

        bitLocation++;
        if (bitLocation >= bytes.length*8)
            return bitLocation;


        selectBit = ((bytes[bitLocation/8])>>(bitLocation%8))&1;
        int pixelGreen = image.getGreenPixelColor(height,width);
        xor = getXorTwoLowBit(pixelGreen) ;

        lsb = (xor == selectBit) ? 0:1;
        image.setGreenPixelColor(height,width,
                (pixelGreen ) ^ (lsb)
        );

        bitLocation++;
        if (bitLocation >= bytes.length*8)
            return bitLocation;


        selectBit = ((bytes[bitLocation/8])>>(bitLocation%8))&1;
        int pixelBlue = image.getBluePixelColor(height,width);
        xor = getXorTwoLowBit(pixelBlue) ;

        lsb = (xor == selectBit) ? 0:1;
        image.setBluePixelColor(height,width,
                (pixelBlue ) ^ (lsb)
        );
        bitLocation++;

        return bitLocation;

    }

    private static int getXorTwoLowBit(int pixelRed) {

        return (pixelRed &1) ^    ((pixelRed>>1)&1);

    }

    private static boolean contain2(List<Integer[]> items,int[] numbers)
    {
        AtomicBoolean result = new AtomicBoolean(false);
        items.forEach((item)->{
            if (item[0]== numbers[0] && item[1] == numbers[1])
                result.set(true);
        });
        return result.get();
    }

    private static float[] findThrashHold(PixelImage image, int length) {
        float th = 1f,tl = 0.005f;
        float lastSaveTH = th,lastSaveTL = tl;
        while (true)
        {

            if ((th-tl) <0.00001f)
            {
                th = lastSaveTH;
                tl = lastSaveTL;
                break;
            }


            float mid = (th + tl)/2;
            CannyEdgeDetector canny = new CannyEdgeDetector(image.getImg(),tl,mid,2);
            canny.process();
            if (canny.getNumberOfEdges() * 3 < length)
            {
                th = mid;
                lastSaveTH = th;
            } else if (canny.getNumberOfEdges() * 3 > 2 * length) {
                lastSaveTH = th;
                lastSaveTL = tl;
                tl = mid;

            }else
            {

                th = mid;
                break;
            }
        }

        return new float[]{th,tl};
    }

    private static PixelImage mask2LSB(PixelImage image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < height;i++)
        {
            for (int j = 0 ; j < width;j++)
            {
                image.setRedPixelColor(i,j,(image.getRedPixelColor(i,j) & 252));
                image.setGreenPixelColor(i,j,(image.getGreenPixelColor(i,j) & 252));
                image.setBluePixelColor(i,j,(image.getBluePixelColor(i,j) & 252));
            }
        }
        return image;
    }

    private static int maxSize(PixelImage image)
    {
        CannyEdgeDetector detector = new CannyEdgeDetector(image.getImg(),0.005f,0.005f,2);
        detector.process();
        return detector.getNumberOfEdges();
    }

    private static int minSize(PixelImage image)
    {
        CannyEdgeDetector detector = new CannyEdgeDetector(image.getImg(),1f,1f,2);
        detector.process();
        return detector.getNumberOfEdges();
    }

    private static List<Integer[]> getNoneSoftPart(PixelImage image){
        CannyEdgeDetector detector = new CannyEdgeDetector(image.getImg(),0.005f,0.005f,2);
        detector.process();
        return detector.getEdges();
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
