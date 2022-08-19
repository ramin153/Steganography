import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DCT {
    PixelImage image;

    private static final int[][] Q =    {{16, 12 ,14 ,14 ,18, 24, 49, 72},
                                        {11 ,12, 13 ,17, 22 ,35, 64 ,92},
                                        {10 ,14 ,16, 22, 37, 55 ,78, 95},
                                        {16 ,19, 24 ,29, 56 ,64 ,87, 98},
                                        {24 ,26 ,40, 51 ,68 ,81, 103, 112},
                                        {40 ,58 ,57, 87 ,109 ,104, 121, 100},
                                        {51 ,60 ,69, 80, 103 ,113 ,120, 103},
                                        {61 ,55 ,56 ,62, 77, 92, 101, 99}};

    public DCT(PixelImage image) {
        this.image = image;
    }

    public void decode(String code) throws Exception {
        byte[] codeBytes = code.getBytes(StandardCharsets.UTF_8);
        codeBytes = Arrays.copyOf(codeBytes,codeBytes.length+1);
        codeBytes[codeBytes.length-1] = 0;
        if (image.getNumberOfPixel() /64 < codeBytes.length * 8 + 8)
            throw new Exception("we need more pixel for handling this text");
        if (image.getWidth()%8!= 0 || image.getHeight()%8!=0)
            throw new Exception("height and width must be factor of 8");
        int bitNumber = 0;
        for (int i = 0 ;i <image.getHeight();i+=8){
            for (int j = 0 ;j <image.getWidth();j+=8){
                for (int k = 0;k<3;k++){
                    byte byteCodeToWrite;
                    if (bitNumber > codeBytes.length *8)
                    {
                        byteCodeToWrite = 0;
                    }else {
                        byteCodeToWrite = codeBytes[bitNumber/8];
                    }
                    int[][] table = eightEightPixelImage(i,j,image,k);



                    table = changeValue(table,-128);

                    double[][] dct = DCTTransform(table);


                    table = quantization(dct);

                    table = addDataToTable(table,byteCodeToWrite,bitNumber%8);

                    //todo here you must find way to save this bit in a format that can save .... i don't know how to it
                    //table = IDCTTransform(unQuantization(table));
                    //table = changeValue(table,+128);
                    //changeColor(image,i,j,table,k);

                }
            }


        }

    }

    public String encode() throws Exception {
        int width = image.getWidth();
        int height = image.getHeight();
        int pixelRGBCount = 0;
        List<Byte> codeBytes = new ArrayList<>();
        byte byteCode = 0;
        if (image.getWidth()%8!= 0 || image.getHeight()%8!=0)
            throw new Exception("height and width must be factor of 8");
        for (int i = 0 ;i <height;i+=8){
            for (int j = 0 ;j <width;j+=8){
                for (int k = 0;k<3;k++){

                    int[][] table = eightEightPixelImage(i,j,image,k);

                    table = changeValue(table,-128);

                    double[][] dct = DCTTransform(table);
                    table = quantization(dct);
                    int data = extractFromTable(table);
                    byteCode+= (data << (pixelRGBCount%8));
                    if (pixelRGBCount%8 == 7)
                    {
                        codeBytes.add(byteCode);
                        byteCode = 0;
                    }
                    pixelRGBCount++;
                    pixelRGBCount = pixelRGBCount % 8;


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

    public static void print2DArrayInt(int[][] inp)
    {
        for (int[] ob:inp)
            System.out.println(Arrays.toString(ob));
    }

    public static void print2DArrayDob(double[][] inp)
    {
        for (double[] ob:inp)
            System.out.println(Arrays.toString(ob));
    }
    public static void changeColor(PixelImage image,int height, int width, int[][] newPixel, int color)
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8;j++){


                //System.out.println(newPixel[i][j]+" "+height+" "+width);
                switch (color)
                {
                    case 0 ->   {image.setRedPixelColor(height+i,width+j,newPixel[i][j]);}
                    case 1 ->   {image.setGreenPixelColor(height+i,width+j,newPixel[i][j]);}
                    default ->  {image.setBluePixelColor(height+i,width+j,newPixel[i][j]);}
                };


            }
        }


    }
    private static int[][] eightEightPixelImage(int height,int width,PixelImage image,int color)
    {
        int[][] table = new int[8][8];
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8;j++){

                int selectColor;

                switch (color)
                {
                    case 0 ->   {selectColor = image.getRedPixelColor(height+i,width+j);}
                    case 1 ->   {selectColor = image.getGreenPixelColor(height+i,width+j);}
                    default ->  { selectColor = image.getBluePixelColor(height+i,width+j);}
                };

                table[i][j] = selectColor;
            }
        }

        return table;
    }

    private static int[][] changeValue(int[][] table,int value)
    {
        for (int i = 0; i < table.length;i++)
        {
            for (int j = 0; j < table[i].length;j++)
            {
                table[i][j] +=value;
            }
        }

        return table;
    }

    private static double[][] DCTTransform(int[][] pixels){
        double[][] result = new double[8][8];

        for (int u = 0; u < 8; u++){
            for (int v = 0; v <8;v++){
                double au,av;
                if (u == 0)
                    au = 1 / Math.sqrt(2);
                else
                    au = 1;

                if (v == 0)
                    av = 1 / Math.sqrt(2);
                else
                    av = 1;

                double save = 0;

                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {

                        save += pixels[i][j] *
                                Math.cos((2 * i + 1) * u * Math.PI / (16)) *
                                Math.cos((2 * j + 1) * v * Math.PI / (16));

                    }
                }

                result[u][v] = (0.25) * save * av * au;

            }
        }

//        for (int i = 0;i < 8;i++)
//        {
//            System.out.println(Arrays.toString(result[i]));
//        }
//        System.out.println();

        return result;
    }
    private static int[][] IDCTTransform(int[][] pixels){
        int[][] result = new int[8][8];

        for (int u = 0; u < 8; u++){
            for (int v = 0; v <8;v++){
                double au,av;
                if (u == 0)
                    au = 1 / Math.sqrt(2);
                else
                    au = 1;

                if (v == 0)
                    av = 1 / Math.sqrt(2);
                else
                    av = 1;

                double save = 0;

                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        save += pixels[i][j] *
                                Math.cos((2 * i + 1) * u * Math.PI / (16)) *
                                Math.cos((2 * j + 1) * v * Math.PI / (16)) * av * au;

                    }
                }

                result[u][v] = (int) Math.round((0.25) * save)  ;

            }
        }

//        for (int i = 0;i < 8;i++)
//        {
//            System.out.println(Arrays.toString(result[i]));
//        }

        return result;
    }

    private static byte extractFromTable(int[][] table)
    {

        if ((table[0][0]&1) == 1)
            return 1;
        else
            return 0;


    }

    private static int zeroOrOne(int... numbers){
        int zero = 0;
        int one = 0;
        for (int i = 0 ; i < 6;i++)
        {
            if (numbers[i]==0)
                zero++;
            else
                one++;
        }
        if (zero > one)
            return zero;
        else if (one > zero)
            return one;

        return (numbers[6]&1);

    }

    private static int[][]  addDataToTable(int[][] table,byte code,int bitNumber)
    {
        int selectBit = (code >> bitNumber%8)&1;

        if (selectBit == 1)
            table[0][0] = table[0][0] | 1;
        else
            table[0][0] = (table[0][0]>>1)<<1;

        int [][] help ;


//        System.out.println();
//        for (int i = 0;i < 8;i++)
//        {
//            System.out.println(Arrays.toString(table[i]));
//        }
//        System.out.println();
        return table;
    }

    private static int getBitInByte(byte code,int bitNumber){
        return 1 & (code >> bitNumber);
    }
    private static int[][] quantization(double[][] table)
    {
        int[][] result = new int[8][8];
        for (int i = 0;i<8;i++){
            for (int j = 0;j<8;j++)
                result[i][j]= (int)Math.round(table[i][j]/(double) Q[i][j]);
        }

//        for (int i = 0;i < 8;i++)
//        {
//            System.out.println(Arrays.toString(result[i]));
//        }
//        System.out.println();

        return result;
    }

    private static int[][] unQuantization(int[][] table)
    {
        int[][] result = new int[8][8];
        for (int i = 0;i<8;i++){
            for (int j = 0;j<8;j++)
                result[i][j]= (int)Math.round(table[i][j]*(double) Q[i][j]);
        }

//        for (int i = 0;i < 8;i++)
//        {
//            System.out.println(Arrays.toString(result[i]));
//        }

        return result;
    }






}
