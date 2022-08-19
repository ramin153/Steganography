import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BPCS {

    private static final int[][] WC = new int[][] {
        {0,1,0,1,0,1,0,1},
        {1,0,1,0,1,0,1,0},
        {0,1,0,1,0,1,0,1},
        {1,0,1,0,1,0,1,0},
        {0,1,0,1,0,1,0,1},
        {1,0,1,0,1,0,1,0},
        {0,1,0,1,0,1,0,1},
        {1,0,1,0,1,0,1,0}
    };

    private PixelImage image;
    private double threshold;

    public BPCS(PixelImage image, double threshold) {
        this.image = image;
        this.threshold = threshold;
    }

    public void decode(String code) throws Exception {
        byte[] codeBytes = code.getBytes(StandardCharsets.UTF_8);
        codeBytes = Arrays.copyOf(codeBytes,codeBytes.length+2);
        codeBytes[codeBytes.length-1]=0;
        codeBytes[codeBytes.length-2]=0;
        int bitNumber = 0;
        boolean isEnded = false;
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height-8 && (!isEnded);i+=8)
        {
            for (int j = 0;j < width-8 && (!isEnded);j+=8)
            {
                for (int k =0; k < 3 && (!isEnded);k++)
                {
                    int[][] pixel = get8X8(i,j,k);


                    int[][] greyCode = tableToGreyCode(pixel,8);
                    int[][][] bitPanel = getBitPlane(greyCode);

                    for (int selectBitPanel = 0 ; selectBitPanel < bitPanel.length;selectBitPanel++)
                    {
                        int[][] table = bitPanel[selectBitPanel];
                        double alpha = complexity(table);
                        if (alpha <= threshold)
                            continue;
                        bitNumber = putBitInTableBPCS(table,codeBytes,bitNumber);

                        alpha = complexity(table);
                        table[0][0] = 0;
                        if (alpha <= threshold){
                            table = xorWithWC(table);
                            table[0][0] = 1;
                        }
                    }
                    int[][] stegGreyCode = convertBitPlane(bitPanel);
                    int[][] stegPixel = tableToGreyCodeInversion(stegGreyCode,8);
                    set8X8(i,j,k,stegPixel);
                    if (bitNumber >= codeBytes.length*8)
                        isEnded = true;
                }
            }
        }

        if (!isEnded)
            throw new Exception("we need bigger image");


    }

    public String encode()
    {
        List<Byte> codeBytes = new ArrayList<>();
        int bitPos =0;
        byte codeByte = 0;
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height-8 ;i+=8)
        {
            for (int j = 0;j < width-8 ;j+=8)
            {
                for (int k =0; k < 3 ;k++)
                {

                    int[][] pixel = get8X8(i,j,k);


                    int[][] greyCode = tableToGreyCode(pixel,8);
                    int[][][] bitPanel = getBitPlane(greyCode);

                    for (int selectBitPanel = 0 ; selectBitPanel < bitPanel.length;selectBitPanel++)
                    {
                        int[][] table = bitPanel[selectBitPanel];
                        double alpha = complexity(table);
                        if (alpha <= threshold)
                            continue;

                        Object[]bitByte = getDataFromGreyCode(bitPos,codeByte,codeBytes,table);
                        bitPos = (int)bitByte[0];
                        codeByte = (byte) bitByte[1];

                    }

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

    private static Object[] getDataFromGreyCode(int bitNumber,byte save,List<Byte> byteCodes,int[][] table)
    {
        if (table[0][0] == 1)
            table = xorWithWC(table);
        for (int i = 0; i < table.length;i++)
        {
            for (int j = 0 ; j < table[0].length;j++)
            {
                if (i == 0 && j == 0)
                    continue;
                int bitSelect = table[i][j]&1;
                save = (byte) (save + ((byte) (bitSelect << bitNumber)));
                bitNumber++;
                if (bitNumber % 8 == 0)
                {
                    bitNumber = 0;
                    byteCodes.add(save);
                    save = 0;
                }
            }
        }
        return new Object[]{bitNumber,save};

    }

    private static int[][] xorWithWC(int[][] table)
    {
        for (int i = 0; i < 8;i++)
        {
            for (int j = 0; j < 8;j++){
                table[i][j] = table[i][j] ^ WC[i][j];
            }
        }
        return table;
    }

    private static int putBitInTableBPCS(int[][] table,byte[] codesByte,int bitNumber)
    {
        for (int i = 0; i < table.length;i++)
        {
            for (int j = 0 ; j < table[0].length;j++)
            {
                if (i == 0 && j == 0)
                    continue;

                table[i][j] = getBitInBytes(codesByte,bitNumber);
                bitNumber++;
            }
        }
        return bitNumber;
    }

    private static int getBitInBytes(byte[] codesByte,int bitNumber)
    {
        if (bitNumber >= codesByte.length*8)
            return 0;
        return (codesByte[bitNumber/8] >> (bitNumber%8))&1;
    }




    private static double complexity(int[][] table)
    {
        double difference = 0;
        for (int i = 0; i < table.length; i++)
        {
            for (int j = 0;j < table[i].length;j++)
            {
                if (i == 0 && j == 0)
                    continue;
                //check if the pixel is has difference in row and col
                if (j != table[i].length - 1 && (table[i][j] != table[i][j+1]))
                    difference++;
                if (i != table.length -1 && (table[i][j] != table[i+1][j]))
                    difference++;

            }
        }

        return difference / (8*8*2 - 16 -2);//8*8*2 - 16 -2=== max difference
    }

    private static int[][][] getBitPlane(int[][] byteCode)
    {
        int[][][] bitPlanes = new int[8][byteCode.length][byteCode[0].length];
        for (int k = 0; k < 8;k++)
        {
            for (int i = 0; i < byteCode.length; i++)
            {
                for (int j = 0;j < byteCode[i].length;j++)
                {
                    bitPlanes[k][i][j] = (byteCode[i][j] >> k) & 1;
                }
            }
        }

        return bitPlanes;
    }

    private static int[][] convertBitPlane(int[][][] bitPlane)
    {
        int[][] table = new int[bitPlane[0].length][bitPlane[0][0].length];
        for (int k = 0; k < 8;k++)
        {
            for (int i = 0; i < table.length; i++)
            {
                for (int j = 0;j < table[i].length;j++)
                {
                    table[i][j] += (bitPlane[k][i][j] & 1) << k;
                }
            }
        }

        return table;
    }

    private static int greyCode(int number,int size)
    {
        int result = 0;
        int oldBit = 0;
        int newBit ;
        for (int i = size -1;i >= 0;i--){
            newBit = getBit(number,i);
            result = result + ( ( newBit ^ oldBit) << i);
            oldBit = newBit;
        }
        return result;
    }

    private static int[][] tableToGreyCode(int[][] table,int size)
    {
        int[][] result = new int[table.length][table[0].length];
        for (int i = 0; i < result.length;i++){
            for (int j = 0; j < result.length;j++)
            {
                result[i][j] = greyCode(table[i][j],8);
            }
        }

        return result;
    }

    private static int[][] tableToGreyCodeInversion(int[][] table,int size)
    {
        int[][] result = new int[table.length][table[0].length];
        for (int i = 0; i < result.length;i++){
            for (int j = 0; j < result.length;j++)
            {
                result[i][j] = greyCodeInverse(table[i][j],8);
            }
        }

        return result;
    }

    private  int[][] get8X8(int height,int width,int color){
        int[][] result = new int[8][8];
        for (int i = 0; i < 8;i++){
            for (int j = 0; j < 8;j++)
            {
                int pixel ;
                switch (color)
                {
                    case 0 ->{
                        pixel = image.getRedPixelColor(height+i,width+j);
                    }
                    case 1 ->{
                        pixel = image.getGreenPixelColor(height+i,width+j);
                    }
                    default ->{
                        pixel = image.getBluePixelColor(height+i,width+j);
                    }
                }
                result[i][j] = pixel;

            }
        }

        return result;
    }


    private  void set8X8(int height,int width,int color,int[][] table){

        for (int i = 0; i < 8;i++){
            for (int j = 0; j < 8;j++)
            {

                switch (color)
                {
                    case 0 ->{
                        image.setRedPixelColor(height+i,width+j,table[i][j]);
                    }
                    case 1 ->{
                        image.setGreenPixelColor(height+i,width+j,table[i][j]);
                    }
                    default ->{
                        image.setBluePixelColor(height+i,width+j,table[i][j]);
                    }
                }


            }
        }


    }

    private static int greyCodeInverse(int number,int size)
    {
        int result = 0;
        int oldBit = 0;
        int newBit ;
        for (int i = size -1;i >= 0;i--){
            newBit = getBit(number,i);
            result = result + ( ( newBit ^ oldBit) << i);
            oldBit = newBit ^ oldBit;
        }
        return result;
    }

    private static int getBit(int number,int bitPos)
    {
        return (number>>bitPos)&1;
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

    public static String toBinary(int n, int len)
    {
        String binary = "";
        for (long i = (1L << len - 1); i > 0; i = i / 2) {
            binary += (n & i) != 0 ? "1" : "0";
        }
        return binary;
    }
}
