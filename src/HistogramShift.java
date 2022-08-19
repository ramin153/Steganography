import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistogramShift {

    PixelImage image;

    public HistogramShift(PixelImage image) {
        this.image = image;
    }

    public void decode(String code) throws Exception
    {
        byte[] codeBytes = code.getBytes(StandardCharsets.UTF_8);
        codeBytes = Arrays.copyOf(codeBytes,codeBytes.length+2);
        codeBytes[codeBytes.length-1]=0;
        codeBytes[codeBytes.length-2]=0;
        int bitNumber = 0;

        for (int i = 0; (bitNumber < codeBytes.length*8) && i < 3;i++)
        {
            int[][] table = getColor(image,i);
            int peak = findPeak(table);
            int[][] shiftTable = shiftBaseOnPeak(table,peak);

            Object[] ob = putData(shiftTable,peak,codeBytes,bitNumber);

            int[][] steg = (int[][]) ob[0];
            bitNumber = (int) ob[1];



            setColor(image,i,steg);
            findPeak(getColor(image,i));
        }

        if(bitNumber < codeBytes.length*8)
            throw new Exception("we cant hide all the bits,we need another image ");


    }

    public String encode()
    {
        int bitNumber = 0;
        byte byteCode = 0;
        List<Byte> bytesCode = new ArrayList<>();

        for (int i = 0 ; i < 3 ; i++)
        {
            int[][] table = getColor(image,i);
            int peak = findPeak(table);

            Object[] ob = getData(table,peak,bitNumber,byteCode,bytesCode);


            bitNumber = (int)ob[0];
            byteCode = (byte) ob[1];


        }

        Byte[] bytesClass = bytesCode.toArray(new Byte[bytesCode.size()]);

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


    private static Object[] getData(int[][] table,int peak,int bitNumber,byte byteCode,List<Byte> bytesCode)
    {
        int peakD2 = peak - 2;
        int peakD1 = peak - 1;
        int peakP2 = peak + 2;
        int peakP1 = peak + 1;
        int size = table.length * table[0].length;




        for (int pos = 2;(pos < size-2) ;pos++)
        {
            int selectPixel = getPixelByPositionDiameter(table,pos);
            if (!(selectPixel == peakD1 || selectPixel == peakP1))
                continue;
            int leftPixel;

            int pixel = getPixelByPositionDiameter(table,pos);
            if (pixel == peakD1){
                leftPixel = getPixelByPositionDiameter(table,pos -1);
                if (leftPixel == peakD2)
                {
                    byteCode =(byte) ((byteCode)+(1<<(bitNumber%8)));
                }
            }else {
                leftPixel = getPixelByPositionDiameter(table,pos -1);
                if (leftPixel == peakP2)
                {
                    byteCode =(byte) ((byteCode)+(1<<(bitNumber%8)));
                }
            }


                bitNumber++;
            if (bitNumber >= 8)
            {

                bitNumber = 0;
                bytesCode.add(byteCode);
                byteCode = 0;
            }
        }

        return new Object[]{bitNumber,byteCode};

    }

    private static Object[] putData(int[][] table,int peak,byte[] bytes,int bitNumber){
        int peakD2 = peak - 2;
        int peakD1 = peak - 1;
        int peakP2 = peak + 2;
        int peakP1 = peak + 1;
        int size = table.length * table[0].length;

        for (int pos = 2;(pos < size-2) && (bitNumber < bytes.length * 8);pos++)
        {
            int selectPixel = getPixelByPositionDiameter(table,pos);
            if (!(selectPixel == peakD2 || selectPixel == peakP2))
                continue;

            int hideBit = getBitInBytes(bytes,bitNumber);
            if (hideBit == 0){
                int leftPixel = getPixelByPositionDiameter(table,pos-1);
                if (!isInIt(leftPixel,peakD2,peakD1,peak,peakP1,peakD2))
                {
                    if (checkTwoSide(table,pos-1,peakD2,peakP2))
                    {
                        if (selectPixel == peakD2)
                        {

                            putInTableBaseOnPositionDiameter(table,pos-1,peakD1);
                        }else
                        {
                            putInTableBaseOnPositionDiameter(table,pos-1,peakP1);
                        }
                        bitNumber++;
                    }
                }
            }else {
                int rightPixel = getPixelByPositionDiameter(table,pos+1);
                if (!isInIt(rightPixel,peakD2,peakD1,peak,peakP1,peakD2))
                {
                    if (checkTwoSide(table,pos+1,peakD2,peakP2))
                    {
                        if (selectPixel == peakD2)
                        {
                            putInTableBaseOnPositionDiameter(table,pos+1,peakD1);
                        }else
                        {
                            putInTableBaseOnPositionDiameter(table,pos+1,peakP1);
                        }
                        bitNumber++;
                    }
                }
            }
        }
        return new Object[]{table,bitNumber};
    }

    private static void putInTableBaseOnPositionDiameter(int[][] table,int position,int value){
        int[] HW = getPositionDiameter(table,position);
        table[HW[0]][HW[1]] = value;
    }
    private static boolean checkTwoSide(int[][] table,int pos,int... notIn)
    {
        int left = getPixelByPositionDiameter(table,pos-1);
        int right = getPixelByPositionDiameter(table,pos+1);

        for (int i : notIn)
        {
            if (left == i && right == i)
                return false;
        }

        return true;

    }
    private static boolean isInIt(int number,int... list){
        for (int i : list)
        {
            if (number == i)
                return true;
        }
        return false;
    }
    private static int getBitInBytes(byte[] bytes,int bitNumber)
    {
        if (bitNumber >= bytes.length*8)
            return 0;
        return (bytes[bitNumber/8] >> (bitNumber % 8)) & 1;
    }

    private static int getPixelByPositionDiameter(int[][] table,int position){
        int[] HW = getPositionDiameter(table,position);
        return table[HW[0]][HW[1]];
    }
    private static int[] getPositionDiameter(int[][] table,int position)
    {
        int height = table.length;
        int width = table[0].length;
        int diameter = (height > width)?width:height;
        int round = 0;
        int change = 1;
        while (position >= change)
        {
            position -= change;
            round++;
            if (round < diameter)
            {
                change +=1;
            }else if(round >= (height+width-diameter))
            {
                change-=1;
            }
        }
        int widthPos = Math.min(round,width - 1);
        int heightPos = Math.max(round-(width -1),0);
        for (int i = position; i > 0; i--){
            widthPos--;
            heightPos++;
        }
        return new int[]{heightPos,widthPos};
    }



    private int[][] shiftBaseOnPeak(int[][] table,int peak)
    {
        int[][] shift = new int[table.length][table[0].length];
        for (int i = 0; i < shift.length;i++){
            for (int j = 0; j <shift[i].length;j++)
            {
                if(table[i][j] > peak  &&  table[i][j] != 255)
                {
                    shift[i][j] = table[i][j]+1;
                }else if(table[i][j] < peak  &&  table[i][j] != 0)
                {
                    shift[i][j] = table[i][j]-1;
                }else
                {
                    shift[i][j] = table[i][j];
                }

            }
        }

        return shift;
    }

    private static int[][] getColor(PixelImage image,int color)
    {
        int[][] table = new int[image.getHeight()][image.getWidth()];

        for (int i = 0; i < image.getHeight();i++)
        {
            for (int j = 0; j < image.getWidth();j++)
            {
                int pixelColor;
                switch (color)
                {
                    case 0 ->{pixelColor = image.getRedPixelColor(i,j);}
                    case 1 ->{pixelColor = image.getGreenPixelColor(i,j);}
                    default ->{pixelColor = image.getBluePixelColor(i,j);}
                }
                table[i][j] = pixelColor;
            }
        }
        return table;
    }

    private static void setColor(PixelImage image,int color,int[][] table)
    {

        for (int i = 0; i < image.getHeight();i++)
        {
            for (int j = 0; j < image.getWidth();j++)
            {

                switch (color)
                {
                    case 0 ->{image.setRedPixelColor(i,j,table[i][j]);}
                    case 1 ->{image.setGreenPixelColor(i,j,table[i][j]);}
                    default ->{image.setBluePixelColor(i,j,table[i][j]);}
                }

            }
        }

    }

    private static int findPeak(int[][] image)
    {
        int peak = 0;
        int countOfPeak = 0;
        //we ignore 255  254 and 0 1
        int[] count = countPixelByNumber(image);
        for (int i = 2; i < count.length-2;i++ )
            if (countOfPeak < count[i])
            {
                peak = i;
                countOfPeak = count[i];
            }




        return peak;
    }

    private static int[] countPixelByNumber(int[][] image)
    {
        int[] count = new int[256];
        for (int i = 0; i < count.length;i++)
        {
            count[i] = 0;
        }

        for (int i = 0; i < image.length;i++){
            for (int j = 0; j <image[i].length;j++)
            {
                count[image[i][j]] = count[image[i][j]] + 1;
            }
        }



        return count;
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
